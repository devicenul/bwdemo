package net.merc.bandwidth.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class ApiConfig implements IApiConfig {
    @Value("bandwidth.apiKey")
    private String apiKey;
    @Value("bandwidth.apiUser")
    private String apiUser;
    @Value("bandwidth.apiSecret")
    private String apiSecret;

    @Override
    public String getApiKey() {
        return apiKey;
    }

    @Override
    public String getApiUser() {
        return apiUser;
    }

    @Override
    public String getApiSecret() {
        return apiSecret;
    }
}
