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
import com.ibm.watson.assistant.v2.Assistant;
import com.ibm.watson.developer_cloud.spring.boot.WatsonAutoConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { WatsonAutoConfiguration.class }, loader = AnnotationConfigContextLoader.class)
@TestPropertySource(properties = { "watson.assistant-v2.url=" + AssistantV2AutoConfigTest.url,
    "watson.assistant-v2.username=" + AssistantV2AutoConfigTest.username,
    "watson.assistant-v2.password=" + AssistantV2AutoConfigTest.password,
    "watson.assistant-v2.versionDate=" + AssistantV2AutoConfigTest.versionDate })
public class AssistantV2AutoConfigTest {

  static final String url = "https://api.us-south.assistant.watson.cloud.ibm.com";
  static final String username = "sam";
  static final String password = "secret";
  static final String versionDate = "2019-11-22";

  @Autowired
  private ApplicationContext applicationContext;

  @Test
  public void assistantBeanConfig() {
    Assistant assistant = (Assistant) applicationContext.getBean("assistantV2");

    assertNotNull(assistant);
    assertEquals(url, assistant.getServiceUrl());

    // Verify the credentials and versionDate -- the latter of which is stored in a private member variable
    try {
      assertEquals(Authenticator.AUTHTYPE_BASIC, assistant.getAuthenticator().authenticationType());
      BasicAuthenticator authenticator = (BasicAuthenticator) assistant.getAuthenticator();
      assertEquals(username, authenticator.getUsername());
      assertEquals(password, authenticator.getPassword());

      Field versionField = Assistant.class.getDeclaredField("versionDate");
      versionField.setAccessible(true);
      assertEquals(versionDate, versionField.get(assistant));
    } catch (NoSuchFieldException | IllegalAccessException ex) {
      // This shouldn't happen
      assert false;
    }
  }
}
