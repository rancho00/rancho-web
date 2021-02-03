package com.rancho.web.actuator.config;

import com.rancho.web.actuator.endpoint.LogEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EndpointConfig {

    @Bean
    public LogEndpoint logEndpoint(){
        return new LogEndpoint();
    }
}
