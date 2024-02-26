package com.store.shop.ai;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

@Configuration
public class OpenAIConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    // This method returns a RestTemplate configured with the provided API key
    public RestTemplate restTemplateWithApiKey(String apiKey) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add((request, body, execution) -> {
            HttpHeaders headers = request.getHeaders();
            headers.add("Authorization",  apiKey);
            return execution.execute(request, body);
        });
        return restTemplate;
    }
}
