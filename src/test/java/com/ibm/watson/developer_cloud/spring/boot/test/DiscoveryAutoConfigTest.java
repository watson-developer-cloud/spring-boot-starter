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

import com.ibm.watson.developer_cloud.discovery.v1.Discovery;
import com.ibm.watson.developer_cloud.service.WatsonService;
import com.ibm.watson.developer_cloud.spring.boot.WatsonAutoConfiguration;

import okhttp3.Credentials;

/**
 * The Class DiscoveryAutoConfigTest.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { WatsonAutoConfiguration.class }, loader = AnnotationConfigContextLoader.class)
@TestPropertySource(properties = { "watson.discovery.url=" + DiscoveryAutoConfigTest.url,
    "watson.discovery.username=" + DiscoveryAutoConfigTest.username,
    "watson.discovery.password=" + DiscoveryAutoConfigTest.password,
    "watson.discovery.versionDate=" + DiscoveryAutoConfigTest.versionDate })
public class DiscoveryAutoConfigTest {

  /** The Constant password. */
  static final String password = "secret";

  /** The Constant url. */
  static final String url = "http://watson.com/discovery";

  /** The Constant username. */
  static final String username = "sam";

  /** The Constant versionDate. */
  static final String versionDate = "2017-12-15";

  @Autowired
  private ApplicationContext applicationContext;

  /**
   * Discovery bean config.
   */
  @Test
  public void discoveryBeanConfig() {
    Discovery discovery = (Discovery) applicationContext.getBean("discovery");

    assertNotNull(discovery);
    assertEquals(url, discovery.getEndPoint());

    // Verify the credentials and versionDate -- which are stored in private member
    // variables
    try {
      Field apiKeyField = WatsonService.class.getDeclaredField("apiKey");
      apiKeyField.setAccessible(true);
      assertEquals(Credentials.basic(username, password), apiKeyField.get(discovery));

      Field versionField = Discovery.class.getDeclaredField("versionDate");
      versionField.setAccessible(true);
      assertEquals(versionDate, versionField.get(discovery));
    } catch (NoSuchFieldException | IllegalAccessException ex) {
      // This shouldn't happen
      assert false;
    }
  }
}
