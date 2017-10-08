package com.avoinovan.drawlot.config;

import com.avoinovan.drawlot.api.UserApi;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import javax.ws.rs.ApplicationPath;

/**
 * @author by avoinovan
 */
@Configuration
@ApplicationPath("/api/v1")
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        registerEndpoints();
    }

    private void registerEndpoints() {
        register(UserApi.class);
    }
}
