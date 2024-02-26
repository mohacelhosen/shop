package com.store.shop.ai.controller;

import com.store.shop.ai.OpenAIConfig;
import com.store.shop.ai.dto.ChatGptRequest;
import com.store.shop.ai.dto.ChatGptResponse;
import com.store.shop.util.ApiResponse;
import com.store.shop.util.Common;
import com.store.shop.util.Error;
import com.store.shop.util.Success;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
@CrossOrigin(origins = {"http://localhost:5173", "*"})
@RequestMapping("/api")
public class AiController {

    private static final Logger logger = LoggerFactory.getLogger(AiController.class);

    @Value("${openai.model}")
    String openAPiModel;

    @Value("${openai.api.url}")
    private String apiUrl;

    @Autowired
    private OpenAIConfig openAIConfig;

    @PostMapping("/chat")
    public ResponseEntity<?> chat(@RequestBody ChatGptRequest chatGptRequest,
                                  @RequestHeader("Authorization") String userApiKey,
                                  HttpServletRequest request) {
        String endpoint = request.getRequestURI();
        String requestId = Common.getRequestId();
        String timeStamp = Common.getTimeStamp();

        logger.info("Api key:: "+userApiKey);
        // Obtain a RestTemplate configured with the user's API key
        RestTemplate userRestTemplate = openAIConfig.restTemplateWithApiKey(userApiKey);


        try {
            ChatGptResponse response = userRestTemplate.postForObject(apiUrl, chatGptRequest, ChatGptResponse.class);
            assert response != null;
            String content = response.getChoices().get(0).getMessage().getContent();
            logger.info(content);
            Success<String> successResponse = ApiResponse.success(timeStamp, requestId, content, endpoint, HttpStatus.OK.value());
            return new ResponseEntity<>(successResponse, HttpStatus.CREATED);
        } catch (HttpClientErrorException.NotFound e) {
            // Log the detailed response from the OpenAI API
            String responseBody = e.getResponseBodyAsString();
            logger.error("OpenAI API returned a 404 Not Found. Response: {}", responseBody);

            Error errorResponse = ApiResponse.error(timeStamp, requestId, endpoint, HttpStatus.NOT_FOUND.value(), "Resource not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }  catch (Exception e) {
            // Log other exceptions
            HttpHeaders requestHeaders = userRestTemplate.headForHeaders(apiUrl);
            logger.error("Failed request details - URL: {}, Headers: {}, Body: {}", apiUrl, requestHeaders, chatGptRequest);

            Error errorResponse = ApiResponse.error(timeStamp, requestId, endpoint, HttpStatus.BAD_REQUEST.value(), e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

    }
}
