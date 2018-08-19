package net.merc.bandwidth.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class demoConfig {
    @Value("bandwidth.apiKey")
    private String apiKey;
    @Value("bandwidth.user")
    private String apiUser;
    @Value("bandwidth.apiSecret")
    private String apiSecret;

    public String getApiKey() {
        return apiKey;
    }

    public String getApiUser() {
        return apiUser;
    }

    public String getApiSecret() {
        return apiSecret;
    }
}
