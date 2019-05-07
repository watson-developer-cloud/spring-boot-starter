/*
 * Copyright © 2017 IBM Corp. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */

package com.ibm.watson.developer_cloud.spring.boot.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Field;

import com.ibm.cloud.sdk.core.service.BaseService;
import com.ibm.watson.developer_cloud.spring.boot.WatsonAutoConfiguration;
import com.ibm.watson.text_to_speech.v1.TextToSpeech;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import okhttp3.Credentials;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { WatsonAutoConfiguration.class }, loader = AnnotationConfigContextLoader.class)
@TestPropertySource(properties = { "watson.text-to-speech.url=" + TextToSpeechAutoConfigTest.url,
    "watson.text-to-speech.username=" + TextToSpeechAutoConfigTest.username,
    "watson.text-to-speech.password=" + TextToSpeechAutoConfigTest.password })
public class TextToSpeechAutoConfigTest {

  static final String url = "http://watson.com/text-to-speech";
  static final String username = "sam";
  static final String password = "secret";

  @Autowired
  private ApplicationContext applicationContext;

  @Test
  public void textToSpeechBeanConfig() {
    TextToSpeech textToSpeech = (TextToSpeech) applicationContext.getBean("textToSpeech");

    assertNotNull(textToSpeech);
    assertEquals(url, textToSpeech.getEndPoint());

    // Verify the credentials -- which are stored in private member variable
    try {
      Field apiKeyField = BaseService.class.getDeclaredField("apiKey");
      apiKeyField.setAccessible(true);
      assertEquals(Credentials.basic(username, password), apiKeyField.get(textToSpeech));
    } catch (NoSuchFieldException | IllegalAccessException ex) {
      // This shouldn't happen
      assert false;
    }
  }
}
