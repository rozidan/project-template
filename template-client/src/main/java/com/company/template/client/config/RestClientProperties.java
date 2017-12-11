package com.company.template.client.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Idan Rozenfeld
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "template.client.api")
public class RestClientProperties {
    /**
     * API location of application service.
     */
    private String path = "http://template:8080/template";

}
