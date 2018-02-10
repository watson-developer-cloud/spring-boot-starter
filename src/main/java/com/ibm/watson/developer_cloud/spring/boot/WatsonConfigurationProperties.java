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

/**
 * The Class WatsonConfigurationProperties.
 */
public class WatsonConfigurationProperties {

  /** Watson service API key. */
  private String apiKey;

  /** Watson service password. */
  private String password;

  /**
   * URL for Watson service. This URL should not include the username or password.
   **/
  private String url;

  /** Watson service username. */
  private String username;

  /** Watson service versionDate. */
  private String versionDate;

  /**
   * Gets the api key.
   *
   * @return the api key
   */
  public String getApiKey() {
    return apiKey;
  }

  /**
   * Gets the password.
   *
   * @return the password
   */
  public String getPassword() {
    return this.password;
  }

  /**
   * Gets the url.
   *
   * @return the url
   */
  public String getUrl() {
    return this.url;
  }

  /**
   * Gets the username.
   *
   * @return the username
   */
  public String getUsername() {
    return this.username;
  }

  /**
   * Gets the version date.
   *
   * @return the version date
   */
  public String getVersionDate() {
    return this.versionDate;
  }

  /**
   * Sets the api key.
   *
   * @param apiKey
   *          the new api key
   */
  public void setApiKey(String apiKey) {
    this.apiKey = apiKey;
  }

  /**
   * Sets the password.
   *
   * @param password
   *          the new password
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * Sets the url.
   *
   * @param url
   *          the new url
   */
  public void setUrl(String url) {
    this.url = url;
  }

  /**
   * Sets the username.
   *
   * @param username
   *          the new username
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * Sets the version date.
   *
   * @param versionDate
   *          the new version date
   */
  public void setVersionDate(String versionDate) {
    this.versionDate = versionDate;
  }
}
