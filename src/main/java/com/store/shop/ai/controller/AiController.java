package com.store.shop.ai.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bot")
public class AiController {

    @Value("${openapi.model}")
    String openAPiModel;

    @Value("${openapi.api.url}")
    private String url;
}
