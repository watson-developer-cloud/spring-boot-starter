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

import com.ibm.watson.developer_cloud.natural_language_understanding.v1.NaturalLanguageUnderstanding;
import com.ibm.watson.developer_cloud.service.WatsonService;
import com.ibm.watson.developer_cloud.spring.boot.WatsonAutoConfiguration;

import okhttp3.Credentials;

/**
 * The Class NaturalLanguageUnderstandingAutoConfigTest.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { WatsonAutoConfiguration.class }, loader = AnnotationConfigContextLoader.class)
@TestPropertySource(properties = {
    "watson.natural-language-understanding.url=" + NaturalLanguageUnderstandingAutoConfigTest.url,
    "watson.natural-language-understanding.username=" + NaturalLanguageUnderstandingAutoConfigTest.username,
    "watson.natural-language-understanding.password=" + NaturalLanguageUnderstandingAutoConfigTest.password,
    "watson.natural-language-understanding.versionDate=" + NaturalLanguageUnderstandingAutoConfigTest.versionDate })
public class NaturalLanguageUnderstandingAutoConfigTest {

  static final String url = "http://watson.com/natural-language-understanding";
  static final String username = "sam";
  static final String password = "secret";

  /** The Constant versionDate. */
  static final String versionDate = "2017-12-15";

  @Autowired
  private ApplicationContext applicationContext;

  /**
   * Natural language understanding bean config.
   */
  @Test
  public void naturalLanguageUnderstandingBeanConfig() {
    NaturalLanguageUnderstanding naturalLanguageUnderstanding = (NaturalLanguageUnderstanding) applicationContext
        .getBean("naturalLanguageUnderstanding");

    assertNotNull(naturalLanguageUnderstanding);
    assertEquals(url, naturalLanguageUnderstanding.getEndPoint());

    // Verify the credentials and versionDate -- which are stored in private member
    // variables
    try {
      Field apiKeyField = WatsonService.class.getDeclaredField("apiKey");
      apiKeyField.setAccessible(true);
      assertEquals(Credentials.basic(username, password), apiKeyField.get(naturalLanguageUnderstanding));

      Field versionField = NaturalLanguageUnderstanding.class.getDeclaredField("versionDate");
      versionField.setAccessible(true);
      assertEquals(versionDate, versionField.get(naturalLanguageUnderstanding));
    } catch (NoSuchFieldException | IllegalAccessException ex) {
      // This shouldn't happen
      assert false;
    }
  }
}
