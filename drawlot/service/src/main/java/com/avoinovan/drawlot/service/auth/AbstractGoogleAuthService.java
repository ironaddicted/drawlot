package com.avoinovan.drawlot.service.auth;

import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleRefreshTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;

import java.io.IOException;
import java.util.Set;

/**
 * Created by avoinovan on 3/10/17
 */
public abstract class AbstractGoogleAuthService {

    private static final String ACCESS_TYPE = "offline";
    private static final String APPROVAL_PROMPT = "force";

    private final HttpTransport transport;
    private final JsonFactory jsonFactory;

    private GoogleAuthorizationCodeFlow googleAuthorizationCodeFlow;


    public AbstractGoogleAuthService(JsonFactory jsonFactory, HttpTransport httpTransport) {
        this.jsonFactory = jsonFactory;
        this.transport = httpTransport;
    }

    public String generateUserRedirectUrl(String userId) {

        final GoogleAuthorizationCodeRequestUrl url = getGoogleAuthorizationCodeFlow().newAuthorizationUrl();

        return url.setRedirectUri(getCallBackUri())
                .setAccessType(ACCESS_TYPE)
                .setApprovalPrompt(APPROVAL_PROMPT)
                .setState(userId)
                .build();
    }

    public String getRefreshToken(final String code) throws IOException {
        GoogleTokenResponse response = getGoogleAuthorizationCodeFlow()
                .newTokenRequest(code)
                .setRedirectUri(getCallBackUri())
                .execute();
        return response.getRefreshToken();
    }

    public String refreshAccessToken(final String refreshToken) throws IOException {

        TokenResponse response =
                new GoogleRefreshTokenRequest(transport, jsonFactory,
                                                     refreshToken, getClientId(), getClientSecret()).execute();

        return response.getAccessToken();
    }

    abstract Set<String> getScopes();

    abstract String getCallBackUri();

    abstract String getClientId();

    abstract String getClientSecret();

    private GoogleAuthorizationCodeFlow getGoogleAuthorizationCodeFlow() {
        if (googleAuthorizationCodeFlow == null) {
            this.googleAuthorizationCodeFlow = new GoogleAuthorizationCodeFlow.Builder(
                    transport, jsonFactory, getClientId(), getClientSecret(), getScopes()
            )
                                                       .build();
        }

        return googleAuthorizationCodeFlow;
    }

}
