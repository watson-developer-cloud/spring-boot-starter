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

import com.ibm.cloud.sdk.core.security.Authenticator;
import com.ibm.cloud.sdk.core.security.BasicAuthenticator;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { WatsonAutoConfiguration.class }, loader = AnnotationConfigContextLoader.class)
@TestPropertySource(properties = { "watson.text-to-speech.url=" + TextToSpeechAutoConfigTest.url,
    "watson.text-to-speech.username=" + TextToSpeechAutoConfigTest.username,
    "watson.text-to-speech.password=" + TextToSpeechAutoConfigTest.password })
public class TextToSpeechAutoConfigTest {

  static final String url = "https://api.us-south.text-to-speech.watson.cloud.ibm.com";
  static final String username = "sam";
  static final String password = "secret";

  @Autowired
  private ApplicationContext applicationContext;

  @Test
  public void textToSpeechBeanConfig() {
    TextToSpeech textToSpeech = (TextToSpeech) applicationContext.getBean("textToSpeech");

    assertNotNull(textToSpeech);
    assertEquals(url, textToSpeech.getServiceUrl());

    // Verify the credentials
    assertEquals(Authenticator.AUTHTYPE_BASIC, textToSpeech.getAuthenticator().authenticationType());
    BasicAuthenticator authenticator = (BasicAuthenticator) textToSpeech.getAuthenticator();
    assertEquals(username, authenticator.getUsername());
    assertEquals(password, authenticator.getPassword());
  }
}
