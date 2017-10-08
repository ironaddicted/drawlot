package com.avoinovan.drawlot.config;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author by avoinovan
 */
@Configuration
public class OAuthConfig {

    @Bean
    public JsonFactory jsonFactory() {
        return new GsonFactory();
    }

    @Bean
    public HttpTransport httpTransport() {
        return new NetHttpTransport();
    }
}
