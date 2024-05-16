package com.citizen.config;

import com.citizen.controller.CitizenController;
import com.citizen.provider.GenericExceptionMapper;
import jakarta.annotation.PostConstruct;
import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
@ApplicationPath("/rest")
public class JerseyConfiguration extends ResourceConfig {

    @PostConstruct
    public void init() {
        register(CitizenController.class);
        register(GenericExceptionMapper.class);
    }

}
