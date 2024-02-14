package com.store.shop;

import jdk.jfr.ContentType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
class HomeController {
    @GetMapping
    public ResponseEntity<byte[]> greeting() throws IOException {
        // Provide the correct path to your image file
        Path imagePath = Path.of("data.txt");

        // Read the image file into a byte array
        byte[] imageBytes = Files.readAllBytes(imagePath);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentLength(imageBytes.length);

        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }
}
