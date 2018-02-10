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
package com.ibm.watson.developer_cloud.spring.boot;

import java.util.Map;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * The Class WatsonServiceCondition.
 */
public class WatsonServiceCondition implements Condition {

  /*
   * (non-Javadoc)
   *
   * @see
   * org.springframework.context.annotation.Condition#matches(org.springframework.
   * context.annotation.ConditionContext,
   * org.springframework.core.type.AnnotatedTypeMetadata)
   */
  @Override
  public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata metadata) {
    Map<String, Object> attributes = metadata
        .getAnnotationAttributes(ConditionalOnWatsonServiceProperties.class.getName());
    String prefix = (String) attributes.get("prefix");

    // If the service is specifically marked as enabled, the condition is true
    if (Boolean.valueOf(conditionContext.getEnvironment().getProperty(prefix + ".enabled"))) {
      return true;
    }

    // If any of the configuration properties for the service are present, the
    // condition is true
    String url = conditionContext.getEnvironment().getProperty(prefix + ".url");
    String username = conditionContext.getEnvironment().getProperty(prefix + ".username");
    String password = conditionContext.getEnvironment().getProperty(prefix + ".password");
    String versionDate = conditionContext.getEnvironment().getProperty(prefix + ".versionDate");
    if (url != null || username != null || password != null || versionDate != null) {
      return true;
    }

    return false;
  }
}
