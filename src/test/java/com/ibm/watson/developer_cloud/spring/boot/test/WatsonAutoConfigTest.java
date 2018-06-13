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

import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText;
import com.ibm.watson.developer_cloud.service.WatsonService;
import com.ibm.watson.developer_cloud.spring.boot.WatsonAutoConfiguration;
import okhttp3.Credentials;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.EnvironmentVariables;
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
@ContextConfiguration(classes = {WatsonAutoConfiguration.class}, loader = AnnotationConfigContextLoader.class)
@TestPropertySource(properties = {"watson.speech-to-text.enabled=true"})
public class WatsonAutoConfigTest {

    private static final String url = "http://watson.com/speech-to-text";
    private static final String username = "sam";
    private static final String password = "secret";

    @ClassRule
    public static final EnvironmentVariables environmentVariables = new EnvironmentVariables();

    static {
        String vcapServices = "{\"speech_to_text\":[{"
                    + "\"credentials\": {"
                        + "\"url\":\"" + url + "\","
                        + "\"username\":\"" + username + "\","
                        + "\"password\":\"" + password + "\""
                    + "},"
                    + "\"plan\": \"free\""
                + "}]}";
        environmentVariables.set("VCAP_SERVICES", vcapServices);
    }

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void watsonBeanConfigFromEnvironment() {
        SpeechToText speechToText = (SpeechToText) applicationContext.getBean("speechToText");

        assertNotNull(speechToText);
        assertEquals(url, speechToText.getEndPoint());

        // Verify the credentials -- which are stored in a private member variable
        try {
            Field apiKeyField = WatsonService.class.getDeclaredField("apiKey");
            apiKeyField.setAccessible(true);
            assertEquals(Credentials.basic(username, password), apiKeyField.get(speechToText));
        } catch (NoSuchFieldException | IllegalAccessException ex) {
            // This shouldn't happen
            assert false;
        }
    }
}
