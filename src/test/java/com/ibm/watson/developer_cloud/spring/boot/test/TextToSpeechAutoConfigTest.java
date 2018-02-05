/*
 * Copyright 2018 IBM Corp. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package com.ibm.watson.developer_cloud.spring.boot.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Field;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.ibm.watson.developer_cloud.service.WatsonService;
import com.ibm.watson.developer_cloud.spring.boot.WatsonAutoConfiguration;
import com.ibm.watson.developer_cloud.text_to_speech.v1.TextToSpeech;

import okhttp3.Credentials;

/**
 * The Class TextToSpeechAutoConfigTest.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { WatsonAutoConfiguration.class }, loader = AnnotationConfigContextLoader.class)
@TestPropertySource(properties = { "watson.text_to_speech.url=" + TextToSpeechAutoConfigTest.url,
    "watson.text_to_speech.username=" + TextToSpeechAutoConfigTest.username,
    "watson.text_to_speech.password=" + TextToSpeechAutoConfigTest.password })
public class TextToSpeechAutoConfigTest {

  /** The Constant password. */
  static final String password = "secret";

  /** The Constant url. */
  static final String url = "http://watson.com/text_to_speech";

  /** The Constant username. */
  static final String username = "sam";

  @Autowired
  private ApplicationContext applicationContext;

  /**
   * Text to speech bean config.
   */
  @Test
  public void textToSpeechBeanConfig() {
    TextToSpeech textToSpeech = (TextToSpeech) applicationContext.getBean("textToSpeech");

    assertNotNull(textToSpeech);
    assertEquals(url, textToSpeech.getEndPoint());

    // Verify the credentials -- which are stored in private member variable
    try {
      Field apiKeyField = WatsonService.class.getDeclaredField("apiKey");
      apiKeyField.setAccessible(true);
      assertEquals(Credentials.basic(username, password), apiKeyField.get(textToSpeech));
    } catch (NoSuchFieldException | IllegalAccessException ex) {
      // This shouldn't happen
      assert false;
    }
  }
}
