package com.testtask.authapi.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("app.internal-service")
public class DataApiProps {
    private String dataApiUrl;
    private String internalToken;

    private Timeout timeout = new Timeout();

    @Data
    public static class Timeout {
        private int connect = 5000;
        private int response = 10000;
        private int read = 5000;
        private int write = 5000;
    }
}