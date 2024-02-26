package com.store.shop.ai;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class OpenAIConfig {

    @Value("${open.api.key}")
    String openAPiKey;

    @Bean
    public RestTemplate template(){
        RestTemplate restTemplate =  new RestTemplate();
        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add("Authorization","Bearer "+openAPiKey);
            return execution.execute(request, body);
        });
        return  restTemplate;
    }
}
