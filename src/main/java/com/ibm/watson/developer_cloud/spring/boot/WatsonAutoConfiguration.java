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

package com.ibm.watson.developer_cloud.spring.boot;

import com.ibm.watson.developer_cloud.assistant.v1.Assistant;
import com.ibm.watson.developer_cloud.conversation.v1.Conversation;
import com.ibm.watson.developer_cloud.discovery.v1.Discovery;
import com.ibm.watson.developer_cloud.language_translator.v3.LanguageTranslator;
import com.ibm.watson.developer_cloud.natural_language_classifier.v1.NaturalLanguageClassifier;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.NaturalLanguageUnderstanding;
import com.ibm.watson.developer_cloud.personality_insights.v3.PersonalityInsights;
import com.ibm.watson.developer_cloud.service.WatsonService;
import com.ibm.watson.developer_cloud.service.security.IamOptions;
import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText;
import com.ibm.watson.developer_cloud.text_to_speech.v1.TextToSpeech;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.ToneAnalyzer;
import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({
    WatsonAssistantConfigurationProperties.class,
    WatsonConversationConfigurationProperties.class,
    WatsonDiscoveryConfigurationProperties.class,
    WatsonLanguageTranslatorConfigurationProperties.class,
    WatsonNaturalLanguageClassifierConfigurationProperties.class,
    WatsonNaturalLanguageUnderstandingConfigurationProperties.class,
    WatsonPersonalityInsightsConfigurationProperties.class,
    WatsonSpeechToTextConfigurationProperties.class,
    WatsonTextToSpeechConfigurationProperties.class,
    WatsonToneAnalyzerConfigurationProperties.class,
    WatsonVisualRecognitionConfigurationProperties.class
})
public class WatsonAutoConfiguration {

  private void configUrl(WatsonService service, WatsonConfigurationProperties config) {
    String url = config.getUrl();
    if (url != null) {
      service.setEndPoint(url);
    }
  }

  private void configBasicAuth(WatsonService service, WatsonConfigurationProperties config) {
    String username = config.getUsername();
    String password = config.getPassword();
    if (username != null && password != null) {
      service.setUsernameAndPassword(username, password);
    }
  }

  private void configApiKey(WatsonService service, WatsonConfigurationProperties config) {
    String apiKey = config.getApiKey();
    if (apiKey != null) {
      service.setApiKey(apiKey);
    }
  }

  private boolean configIamApiKey(WatsonService service, WatsonConfigurationProperties config) {
    String iamApiKey = config.getIamApiKey();
    if (iamApiKey != null) {
      IamOptions options = new IamOptions.Builder().apiKey(iamApiKey).build();
      service.setIamCredentials(options);
      return true;
    }
    return false;
  }

  // Watson Assistant service

  @Autowired
  private WatsonAssistantConfigurationProperties assistantConfig;

  @Bean
  @ConditionalOnMissingBean
  @ConditionalOnWatsonServiceProperties(prefix = WatsonAssistantConfigurationProperties.PREFIX)
  public Assistant assistant() {
    Assistant service = new Assistant(assistantConfig.getVersionDate());
    configUrl(service, assistantConfig);
    if (!configIamApiKey(service, assistantConfig)) {
      configBasicAuth(service, assistantConfig);
    }
    return service;
  }

  // Watson Conversation service

  @Autowired
  private WatsonConversationConfigurationProperties conversationConfig;

  @Bean
  @ConditionalOnMissingBean
  @ConditionalOnWatsonServiceProperties(prefix = WatsonConversationConfigurationProperties.PREFIX)
  public Conversation conversation() {
    Conversation service = new Conversation(conversationConfig.getVersionDate());
    configUrl(service, conversationConfig);
    if (!configIamApiKey(service, conversationConfig)) {
      configBasicAuth(service, conversationConfig);
    }
    return service;
  }

  // Watson Discovery service

  @Autowired
  private WatsonDiscoveryConfigurationProperties discoveryConfig;

  @Bean
  @ConditionalOnMissingBean
  @ConditionalOnWatsonServiceProperties(prefix = WatsonDiscoveryConfigurationProperties.PREFIX)
  public Discovery discovery() {
    Discovery service = new Discovery(discoveryConfig.getVersionDate());
    configUrl(service, discoveryConfig);
    if (!configIamApiKey(service, discoveryConfig)) {
      configBasicAuth(service, discoveryConfig);
    }
    return service;
  }

  // Watson LanguageTranslator service

  @Autowired
  private WatsonLanguageTranslatorConfigurationProperties ltConfig;

  @Bean
  @ConditionalOnMissingBean
  @ConditionalOnWatsonServiceProperties(prefix = WatsonLanguageTranslatorConfigurationProperties.PREFIX)
  public LanguageTranslator languageTranslator() {
    LanguageTranslator service = new LanguageTranslator(ltConfig.getVersionDate());
    configUrl(service, ltConfig);
    if (!configIamApiKey(service, ltConfig)) {
      configBasicAuth(service, ltConfig);
    }
    return service;
  }

  // Watson NaturalLanguageClassifier service

  @Autowired
  private WatsonNaturalLanguageClassifierConfigurationProperties nlcConfig;

  @Bean
  @ConditionalOnMissingBean
  @ConditionalOnWatsonServiceProperties(prefix = WatsonNaturalLanguageClassifierConfigurationProperties.PREFIX)
  public NaturalLanguageClassifier naturalLanguageClassifier() {
    NaturalLanguageClassifier service = new NaturalLanguageClassifier();
    configUrl(service, nlcConfig);
    if (!configIamApiKey(service, nlcConfig)) {
      configBasicAuth(service, nlcConfig);
    }
    return service;
  }

  // Watson NaturalLanguageUnderstanding service

  @Autowired
  private WatsonNaturalLanguageUnderstandingConfigurationProperties nluConfig;

  @Bean
  @ConditionalOnMissingBean
  @ConditionalOnWatsonServiceProperties(prefix = WatsonNaturalLanguageUnderstandingConfigurationProperties.PREFIX)
  public NaturalLanguageUnderstanding naturalLanguageUnderstanding() {
    NaturalLanguageUnderstanding service = new NaturalLanguageUnderstanding(nluConfig.getVersionDate());
    configUrl(service, nluConfig);
    if (!configIamApiKey(service, nluConfig)) {
      configBasicAuth(service, nluConfig);
    }
    return service;
  }

  // Watson PersonalityInsights service

  @Autowired
  private WatsonPersonalityInsightsConfigurationProperties piConfig;

  @Bean
  @ConditionalOnMissingBean
  @ConditionalOnWatsonServiceProperties(prefix = WatsonPersonalityInsightsConfigurationProperties.PREFIX)
  public PersonalityInsights personalityInsights() {
    PersonalityInsights service = new PersonalityInsights(piConfig.getVersionDate());
    configUrl(service, piConfig);
    if (!configIamApiKey(service, piConfig)) {
      configBasicAuth(service, piConfig);
    }
    return service;
  }

  // Watson SpeechToText service

  @Autowired
  private WatsonSpeechToTextConfigurationProperties sttConfig;

  @Bean
  @ConditionalOnMissingBean
  @ConditionalOnWatsonServiceProperties(prefix = WatsonSpeechToTextConfigurationProperties.PREFIX)
  public SpeechToText speechToText() {
    SpeechToText service = new SpeechToText();
    configUrl(service, sttConfig);
    if (!configIamApiKey(service, sttConfig)) {
      configBasicAuth(service, sttConfig);
    }
    return service;
  }

  // Watson TextToSpeech service

  @Autowired
  private WatsonTextToSpeechConfigurationProperties ttsConfig;

  @Bean
  @ConditionalOnMissingBean
  @ConditionalOnWatsonServiceProperties(prefix = WatsonTextToSpeechConfigurationProperties.PREFIX)
  public TextToSpeech textToSpeech() {
    TextToSpeech service = new TextToSpeech();
    configUrl(service, ttsConfig);
    if (!configIamApiKey(service, ttsConfig)) {
      configBasicAuth(service, ttsConfig);
    }
    return service;
  }

  // Watson ToneAnalyzer service

  @Autowired
  private WatsonToneAnalyzerConfigurationProperties taConfig;

  @Bean
  @ConditionalOnMissingBean
  @ConditionalOnWatsonServiceProperties(prefix = WatsonToneAnalyzerConfigurationProperties.PREFIX)
  public ToneAnalyzer toneAnalyzer() {
    ToneAnalyzer service = new ToneAnalyzer(taConfig.getVersionDate());
    configUrl(service, taConfig);
    if (!configIamApiKey(service, taConfig)) {
      configBasicAuth(service, taConfig);
    }
    return service;
  }

  // Watson VisualRecognition service

  @Autowired
  private WatsonVisualRecognitionConfigurationProperties vrConfig;

  @Bean
  @ConditionalOnMissingBean
  @ConditionalOnWatsonServiceProperties(prefix = WatsonVisualRecognitionConfigurationProperties.PREFIX)
  public VisualRecognition visualRecognition() {
    VisualRecognition service = new VisualRecognition(vrConfig.getVersionDate());
    configUrl(service, vrConfig);
    if (!configIamApiKey(service, vrConfig)) {
      configBasicAuth(service, vrConfig);
      configApiKey(service, vrConfig);
    }
    return service;
  }

}
