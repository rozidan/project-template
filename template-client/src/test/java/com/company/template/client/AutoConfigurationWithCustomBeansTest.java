package com.company.template.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.mock.http.client.MockClientHttpResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

/**
 * @author Idan Rozenfeld
 */
@RunWith(SpringRunner.class)
@SpringBootTest(properties = "template.client.api.path=someServer")
public class AutoConfigurationWithCustomBeansTest {

    @Autowired(required = false)
    private RestTemplate restTemplate;

    @Test
    public void shouldInstantiateCustomRestTemplate() {
        assertThat(restTemplate.getInterceptors(), hasSize(1));
    }

    @Configuration
    @EnableAutoConfiguration
    public static class Application {
        @Bean
        public RestTemplate restTemplate(RestTemplateBuilder builder) {
            return builder.interceptors((request, body, execution) ->
                    new MockClientHttpResponse(new byte[]{}, HttpStatus.OK)).build();
        }
    }

}

