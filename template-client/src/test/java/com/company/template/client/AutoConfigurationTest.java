package com.company.template.client;

import com.company.template.client.api.ProductRestClient;
import com.company.template.client.config.RestClientProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

/**
 * @author Idan Rozenfeld
 */
@RunWith(SpringRunner.class)
@SpringBootTest(properties = "template.client.api.path=someServer")
public class AutoConfigurationTest {

    @Autowired(required = false)
    private RestTemplate restTemplate;

    @Autowired(required = false)
    private RestClientProperties properties;

    @Autowired(required = false)
    private ProductRestClient client;

    @Test
    public void shouldInstantiateRestClient() {
        assertThat(client, is(notNullValue()));
    }

    @Test
    public void shouldInstantiateDefaultRestTemplate() {
        assertThat(restTemplate, is(notNullValue()));
        assertThat(restTemplate.getInterceptors(), hasSize(0));
    }

    @Test
    public void shouldInstantiateProperties() {
        assertThat(properties, is(notNullValue()));
    }

    @Test
    public void shouldOverrideDefaultProperty() {
        assertThat(properties.getPath(), is(equalTo("someServer")));
    }

    @Configuration
    @EnableAutoConfiguration
    public static class Application {

    }

}

