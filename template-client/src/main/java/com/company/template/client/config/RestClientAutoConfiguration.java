package com.company.template.client.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author Idan Rozenfeld
 */
@EnableConfigurationProperties(RestClientProperties.class)
public class RestClientAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Configuration
    @ComponentScan(basePackages = "com.company.template.client.api")
    public static class RestApiConfiguration {

    }
}
