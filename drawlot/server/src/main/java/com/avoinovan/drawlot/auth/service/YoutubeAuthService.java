package com.avoinovan.drawlot.auth.service;

import com.avoinovan.drawlot.model.repository.UserRepository;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by avoinovan on 3/3/17
 */
@Service
public class YoutubeAuthService extends AbstractGoogleAuthService {

    @Value("${youtube.auth.scope}")
    private String scope;

    @Value("${google.oauth2.redirect_uris}")
    private String callbackURI;

    @Value("${google.oauth2.client_id}")
    private String clientId;

    @Value("${google.oauth2.client_secret}")
    private String sclientSecret;

    private static  Set<String> SCOPE_SET;

    public YoutubeAuthService(JsonFactory jsonFactory, HttpTransport httpTransport) {
        super(jsonFactory, httpTransport);
    }

    @Override
    Set<String> getScopes() {

        if (SCOPE_SET == null) {
            SCOPE_SET = new HashSet<>(Collections.singletonList(scope));
        }

        return SCOPE_SET;
    }

    @Override
    String getCallBackUri() {
        return callbackURI;
    }

    @Override
    String getClientId() {
        return clientId;
    }

    @Override
    String getClientSecret() {
        return sclientSecret;
    }
}
