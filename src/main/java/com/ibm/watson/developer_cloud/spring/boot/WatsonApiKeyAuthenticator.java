package com.ibm.watson.developer_cloud.spring.boot;

import com.ibm.cloud.sdk.core.http.HttpHeaders;
import com.ibm.cloud.sdk.core.security.Authenticator;
import okhttp3.Request;
import org.apache.commons.lang3.StringUtils;

import static com.ibm.cloud.sdk.core.security.AuthenticatorBase.ERRORMSG_PROP_MISSING;

/**
 * {@link Authenticator} implementation to allow for authentication using the old API key method in Visual Recognition.
 */
public class WatsonApiKeyAuthenticator implements Authenticator {
  private String apiKey;

  public WatsonApiKeyAuthenticator(String apiKey) {
    this.apiKey = apiKey;
    this.validate();
  }

  @Override
  public void validate() {
    if (StringUtils.isEmpty(apiKey)) {
      throw new IllegalArgumentException(String.format(ERRORMSG_PROP_MISSING, "apiKey"));
    }
  }

  @Override
  public String authenticationType() {
    return "apiKey";
  }

  @Override
  public void authenticate(Request.Builder builder) {
    builder.addHeader(HttpHeaders.AUTHORIZATION, "Basic " + this.apiKey);
  }

  /**
   * @return the API key configured on this Authenticator
   */
  public String getApiKey() {
    return this.apiKey;
  }
}
