package com.pskwiercz.jugragspringai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.ClientHttpRequestFactories;
import org.springframework.boot.web.client.ClientHttpRequestFactorySettings;
import org.springframework.boot.web.client.RestClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.BufferingClientHttpRequestFactory;

import java.time.Duration;

@SpringBootApplication
public class JugRagSpringAiApplication {

    public static void main(String[] args) {
        SpringApplication.run(JugRagSpringAiApplication.class, args);
    }

    /** Increase HTTP timeouts
     * In Spring Boot 3.4 timeouts can be set via configuration properties:
     *      spring.http.client.connect-timeout:30s
     *      spring.http.client.read-timeout:120s
     */
    @Bean
    RestClientCustomizer restClientCustomizer() {
        return restClientBuilder -> {
            restClientBuilder
                .requestFactory(new BufferingClientHttpRequestFactory(
                    ClientHttpRequestFactories.get(ClientHttpRequestFactorySettings.DEFAULTS
                        .withConnectTimeout(Duration.ofSeconds(60))
                        .withReadTimeout(Duration.ofSeconds(120))
                )));
        };
    }
}
