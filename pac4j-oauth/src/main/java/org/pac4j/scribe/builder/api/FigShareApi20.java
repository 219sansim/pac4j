package org.pac4j.scribe.builder.api;

import com.github.scribejava.core.builder.api.DefaultApi20;
import com.github.scribejava.core.httpclient.HttpClient;
import com.github.scribejava.core.httpclient.HttpClientConfig;
import com.github.scribejava.core.model.OAuthConstants;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.oauth.AccessTokenRequestParams;
import com.github.scribejava.core.oauth.OAuth20Service;
import lombok.val;

import java.io.OutputStream;

/**
 * <p>FigShareApi20 class.</p>
 *
 * @author Vassilis Virvilis
 * @since 3.8.0
 */
public class FigShareApi20 extends DefaultApi20 {
    public static class Service extends OAuth20Service {
        public Service(DefaultApi20 api, String apiKey, String apiSecret, String callback, String defaultScope, String responseType,
                OutputStream debugStream, String userAgent, HttpClientConfig httpClientConfig, HttpClient httpClient) {
            super(api, apiKey, apiSecret, callback, defaultScope, responseType, debugStream, userAgent, httpClientConfig, httpClient);
        }

        @Override
        protected OAuthRequest createAccessTokenRequest(AccessTokenRequestParams params) {
            val request = super.createAccessTokenRequest(params);
            request.addParameter(OAuthConstants.CLIENT_ID, getApiKey());
            request.addParameter(OAuthConstants.CLIENT_SECRET, getApiSecret());
            return request;
        }
    }

    /** {@inheritDoc} */
    @Override
    public OAuth20Service createService(String apiKey, String apiSecret, String callback, String defaultScope, String responseType,
            OutputStream debugStream, String userAgent, HttpClientConfig httpClientConfig, HttpClient httpClient) {
        return new Service(this, apiKey, apiSecret, callback, defaultScope, responseType, debugStream, userAgent, httpClientConfig,
                httpClient);
    }

    /** {@inheritDoc} */
    @Override
    public String getAccessTokenEndpoint() {
        return "https://api.figshare.com/v2/token";
    }

    /** {@inheritDoc} */
    @Override
    protected String getAuthorizationBaseUrl() {
        return "https://figshare.com/account/applications/authorize";
    }
}
