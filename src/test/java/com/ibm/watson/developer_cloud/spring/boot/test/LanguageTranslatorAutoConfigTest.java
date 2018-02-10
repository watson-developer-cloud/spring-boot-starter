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

import com.ibm.watson.developer_cloud.language_translator.v2.LanguageTranslator;
import com.ibm.watson.developer_cloud.service.WatsonService;
import com.ibm.watson.developer_cloud.spring.boot.WatsonAutoConfiguration;

import okhttp3.Credentials;

/**
 * The Class LanguageTranslatorAutoConfigTest.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WatsonAutoConfiguration.class}, loader = AnnotationConfigContextLoader.class)
@TestPropertySource(properties = {
    "watson.language-translator.url=" + LanguageTranslatorAutoConfigTest.url,
    "watson.language-translator.username=" + LanguageTranslatorAutoConfigTest.username,
    "watson.language-translator.password=" + LanguageTranslatorAutoConfigTest.password
})
public class LanguageTranslatorAutoConfigTest {

  static final String url = "http://watson.com/language-translator";
  static final String username = "sam";

  @Autowired
  private ApplicationContext applicationContext;

  /**
   * Language translator bean config.
   */
  @Test
  public void languageTranslatorBeanConfig() {
    LanguageTranslator languageTranslator = (LanguageTranslator) applicationContext.getBean("languageTranslator");

    assertNotNull(languageTranslator);
    assertEquals(url, languageTranslator.getEndPoint());

    // Verify the credentials -- which are stored in a private member variables
    try {
      Field apiKeyField = WatsonService.class.getDeclaredField("apiKey");
      apiKeyField.setAccessible(true);
      assertEquals(Credentials.basic(username, password), apiKeyField.get(languageTranslator));
    } catch (NoSuchFieldException | IllegalAccessException ex) {
      // This shouldn't happen
      assert false;
    }
  }
}
