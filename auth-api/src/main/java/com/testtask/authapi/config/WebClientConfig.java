package com.testtask.authapi.config;

import com.testtask.authapi.config.properties.DataApiProps;
import io.netty.channel.ChannelOption;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@RequiredArgsConstructor
@Configuration
public class WebClientConfig {
    private final DataApiProps props;

    @Bean
    public WebClient internalServiceWebClient() {
        var timeout = props.getTimeout();

        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, timeout.getConnect())
                .responseTimeout(Duration.ofMillis(timeout.getResponse()));

        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
