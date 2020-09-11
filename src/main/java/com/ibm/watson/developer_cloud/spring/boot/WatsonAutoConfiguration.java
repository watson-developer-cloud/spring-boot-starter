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

import com.ibm.cloud.sdk.core.security.Authenticator;
import com.ibm.cloud.sdk.core.security.BasicAuthenticator;
import com.ibm.cloud.sdk.core.security.ConfigBasedAuthenticatorFactory;
import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.cloud.sdk.core.service.BaseService;
import com.ibm.watson.assistant.v1.Assistant;
import com.ibm.watson.compare_comply.v1.CompareComply;
import com.ibm.watson.discovery.v1.Discovery;
import com.ibm.watson.language_translator.v3.LanguageTranslator;
import com.ibm.watson.natural_language_classifier.v1.NaturalLanguageClassifier;
import com.ibm.watson.natural_language_understanding.v1.NaturalLanguageUnderstanding;
import com.ibm.watson.personality_insights.v3.PersonalityInsights;
import com.ibm.watson.speech_to_text.v1.SpeechToText;
import com.ibm.watson.text_to_speech.v1.TextToSpeech;
import com.ibm.watson.tone_analyzer.v3.ToneAnalyzer;
import com.ibm.watson.visual_recognition.v3.VisualRecognition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({ WatsonAssistantConfigurationProperties.class,
    WatsonAssistantV2ConfigurationProperties.class,
    WatsonCompareComplyConfigurationProperties.class, WatsonDiscoveryConfigurationProperties.class,
    WatsonDiscoveryV2ConfigurationProperties.class,
    WatsonLanguageTranslatorConfigurationProperties.class, WatsonNaturalLanguageClassifierConfigurationProperties.class,
    WatsonNaturalLanguageUnderstandingConfigurationProperties.class,
    WatsonPersonalityInsightsConfigurationProperties.class, WatsonSpeechToTextConfigurationProperties.class,
    WatsonTextToSpeechConfigurationProperties.class, WatsonToneAnalyzerConfigurationProperties.class,
    WatsonVisualRecognitionConfigurationProperties.class })
public class WatsonAutoConfiguration {

  private void configUrl(BaseService service, WatsonConfigurationProperties config) {
    String url = config.getUrl();
    if (url != null) {
      service.setServiceUrl(url);
    }
  }

  private Authenticator configAuth(WatsonConfigurationProperties config, String serviceName) {
    String iamApiKey = config.getIamApiKey();
    if (iamApiKey != null) {
      return new IamAuthenticator(iamApiKey);
    }
    String username = config.getUsername();
    String password = config.getPassword();
    if (username != null && password != null) {
      return new BasicAuthenticator(username, password);
    }
    String apiKey = config.getApiKey();
    if (apiKey != null) {
      return new WatsonApiKeyAuthenticator(apiKey);
    }

    // If we can't find the right properties, we'll return what we get from the auth config factory, which will
    // pull from things like VCAP_SERVICES.
    return ConfigBasedAuthenticatorFactory.getAuthenticator(serviceName);
  }

  // Watson Assistant service V1

  @Autowired
  private WatsonAssistantConfigurationProperties assistantConfig;

  @Bean
  @ConditionalOnMissingBean
  @ConditionalOnWatsonServiceProperties(prefix = WatsonAssistantConfigurationProperties.PREFIX)
  public Assistant assistant() {
    Authenticator authConfig = configAuth(assistantConfig, "assistant");
    Assistant service = new Assistant(assistantConfig.getVersionDate(), authConfig);
    configUrl(service, assistantConfig);
    return service;
  }

  // Watson Assistant service V2

  @Autowired
  private WatsonAssistantV2ConfigurationProperties assistantV2Config;

  @Bean
  @ConditionalOnMissingBean
  @ConditionalOnWatsonServiceProperties(prefix = WatsonAssistantV2ConfigurationProperties.PREFIX)
  public com.ibm.watson.assistant.v2.Assistant assistantV2() {
    Authenticator authConfig = configAuth(assistantV2Config, "assistant_v2");
    com.ibm.watson.assistant.v2.Assistant service =
            new com.ibm.watson.assistant.v2.Assistant(assistantV2Config.getVersionDate(), authConfig);
    configUrl(service, assistantConfig);
    return service;
  }

  // Watson Compare and Comply

  @Autowired
  private WatsonCompareComplyConfigurationProperties compareComplyConfig;

  @Bean
  @ConditionalOnMissingBean
  @ConditionalOnWatsonServiceProperties(prefix = WatsonCompareComplyConfigurationProperties.PREFIX)
  public CompareComply compareComply() {
    Authenticator authConfig = configAuth(compareComplyConfig, "compare_comply");
    CompareComply service = new CompareComply(compareComplyConfig.getVersionDate(), authConfig);
    configUrl(service, compareComplyConfig);
    return service;
  }

  // Watson Discovery service V1

  @Autowired
  private WatsonDiscoveryConfigurationProperties discoveryConfig;

  @Bean
  @ConditionalOnMissingBean
  @ConditionalOnWatsonServiceProperties(prefix = WatsonDiscoveryConfigurationProperties.PREFIX)
  public Discovery discovery() {
    Authenticator authConfig = configAuth(discoveryConfig, "discovery");
    Discovery service = new Discovery(discoveryConfig.getVersionDate(), authConfig);
    configUrl(service, discoveryConfig);
    return service;
  }

  // Watson Discovery service V2

  @Autowired
  private WatsonDiscoveryV2ConfigurationProperties discoveryV2Config;

  @Bean
  @ConditionalOnMissingBean
  @ConditionalOnWatsonServiceProperties(prefix = WatsonDiscoveryV2ConfigurationProperties.PREFIX)
  public com.ibm.watson.discovery.v2.Discovery discoveryV2() {
    Authenticator authConfig = configAuth(discoveryV2Config, "discovery_v2");
    com.ibm.watson.discovery.v2.Discovery service =
            new com.ibm.watson.discovery.v2.Discovery(discoveryV2Config.getVersionDate(), authConfig);
    configUrl(service, discoveryV2Config);
    return service;
  }

  // Watson LanguageTranslator service

  @Autowired
  private WatsonLanguageTranslatorConfigurationProperties ltConfig;

  @Bean
  @ConditionalOnMissingBean
  @ConditionalOnWatsonServiceProperties(prefix = WatsonLanguageTranslatorConfigurationProperties.PREFIX)
  public LanguageTranslator languageTranslator() {
    Authenticator authConfig = configAuth(ltConfig, "language_translator");
    LanguageTranslator service = new LanguageTranslator(ltConfig.getVersionDate(), authConfig);
    configUrl(service, ltConfig);
    return service;
  }

  // Watson NaturalLanguageClassifier service

  @Autowired
  private WatsonNaturalLanguageClassifierConfigurationProperties nlcConfig;

  @Bean
  @ConditionalOnMissingBean
  @ConditionalOnWatsonServiceProperties(prefix = WatsonNaturalLanguageClassifierConfigurationProperties.PREFIX)
  public NaturalLanguageClassifier naturalLanguageClassifier() {
    Authenticator authConfig = configAuth(nlcConfig, "natural_language_classifier");
    NaturalLanguageClassifier service = new NaturalLanguageClassifier(authConfig);
    configUrl(service, nlcConfig);
    return service;
  }

  // Watson NaturalLanguageUnderstanding service

  @Autowired
  private WatsonNaturalLanguageUnderstandingConfigurationProperties nluConfig;

  @Bean
  @ConditionalOnMissingBean
  @ConditionalOnWatsonServiceProperties(prefix = WatsonNaturalLanguageUnderstandingConfigurationProperties.PREFIX)
  public NaturalLanguageUnderstanding naturalLanguageUnderstanding() {
    Authenticator authConfig = configAuth(nluConfig, "natural_language_understanding");
    NaturalLanguageUnderstanding service = new NaturalLanguageUnderstanding(nluConfig.getVersionDate(), authConfig);
    configUrl(service, nluConfig);
    return service;
  }

  // Watson PersonalityInsights service

  @Autowired
  private WatsonPersonalityInsightsConfigurationProperties piConfig;

  @Bean
  @ConditionalOnMissingBean
  @ConditionalOnWatsonServiceProperties(prefix = WatsonPersonalityInsightsConfigurationProperties.PREFIX)
  public PersonalityInsights personalityInsights() {
    Authenticator authConfig = configAuth(piConfig, "personality_insights");
    PersonalityInsights service = new PersonalityInsights(piConfig.getVersionDate(), authConfig);
    configUrl(service, piConfig);
    return service;
  }

  // Watson SpeechToText service

  @Autowired
  private WatsonSpeechToTextConfigurationProperties sttConfig;

  @Bean
  @ConditionalOnMissingBean
  @ConditionalOnWatsonServiceProperties(prefix = WatsonSpeechToTextConfigurationProperties.PREFIX)
  public SpeechToText speechToText() {
    Authenticator authConfig = configAuth(sttConfig, "speech_to_text");
    SpeechToText service = new SpeechToText(authConfig);
    configUrl(service, sttConfig);
    return service;
  }

  // Watson TextToSpeech service

  @Autowired
  private WatsonTextToSpeechConfigurationProperties ttsConfig;

  @Bean
  @ConditionalOnMissingBean
  @ConditionalOnWatsonServiceProperties(prefix = WatsonTextToSpeechConfigurationProperties.PREFIX)
  public TextToSpeech textToSpeech() {
    Authenticator authConfig = configAuth(ttsConfig, "text_to_speech");
    TextToSpeech service = new TextToSpeech(authConfig);
    configUrl(service, ttsConfig);
    return service;
  }

  // Watson ToneAnalyzer service

  @Autowired
  private WatsonToneAnalyzerConfigurationProperties taConfig;

  @Bean
  @ConditionalOnMissingBean
  @ConditionalOnWatsonServiceProperties(prefix = WatsonToneAnalyzerConfigurationProperties.PREFIX)
  public ToneAnalyzer toneAnalyzer() {
    Authenticator authConfig = configAuth(taConfig, "tone_analyzer");
    ToneAnalyzer service = new ToneAnalyzer(taConfig.getVersionDate(), authConfig);
    configUrl(service, taConfig);
    return service;
  }

  // Watson VisualRecognition service

  @Autowired
  private WatsonVisualRecognitionConfigurationProperties vrConfig;

  @Bean
  @ConditionalOnMissingBean
  @ConditionalOnWatsonServiceProperties(prefix = WatsonVisualRecognitionConfigurationProperties.PREFIX)
  public VisualRecognition visualRecognition() {
    Authenticator authConfig = configAuth(vrConfig, "visual_recognition");
    VisualRecognition service = new VisualRecognition(vrConfig.getVersionDate(), authConfig);
    configUrl(service, vrConfig);
    return service;
  }

}
