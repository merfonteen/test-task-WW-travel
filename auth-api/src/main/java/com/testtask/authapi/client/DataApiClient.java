package com.testtask.authapi.client;

import com.testtask.authapi.config.properties.DataApiProps;
import com.testtask.authapi.dto.ProcessRequest;
import com.testtask.authapi.dto.ProcessResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;

@Component
public class DataApiClient {
    private final DataApiProps props;
    private final WebClient webClient;

    public DataApiClient(WebClient baseClient, DataApiProps props) {
        this.props = props;
        this.webClient = baseClient.mutate()
                .baseUrl(props.getDataApiUrl())
                .defaultHeader("X-Internal-Token", props.getInternalToken())
                .build();
    }

    public ProcessResponse callDataApiProcess(ProcessRequest processRequest) {
        return webClient.
                post()
                .uri("/api/v1/transform")
                .header("X-Internal-Token", props.getInternalToken())
                .bodyValue(processRequest)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, r ->
                        r.bodyToMono(String.class).map(msg ->
                                new ResponseStatusException(403, "data-api rejected", null)))
                .onStatus(HttpStatusCode::is5xxServerError, r ->
                        r.bodyToMono(String.class).map(msg ->
                                new ResponseStatusException(502, "data-api 5xx", null)))
                .bodyToMono(ProcessResponse.class)
                .block(Duration.ofMillis(props.getTimeout().getResponse() + 1000L));
    }
}
