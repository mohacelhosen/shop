package com.store.shop;

import jdk.jfr.ContentType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/audio/{audioName}")
    public ResponseEntity<?> audio(@PathVariable String audioName) throws IOException {
        String audioFolder = "audio";
        List<String> audioList = listFilesInFolder(audioFolder);

        Path path = Path.of("audio/"+audioName.toLowerCase()+".mp3");
        byte[] audioBytes = Files.readAllBytes(path);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("audio/mpeg"));
        headers.setContentLength(audioBytes.length);
        headers.setCacheControl("no-cache, no-store, must-revalidate");
        headers.setPragma("no-cache");
        headers.setExpires(0);

        return new ResponseEntity<>(audioBytes,headers,HttpStatus.OK);
    }
    private static List<String> listFilesInFolder(String folderPath) throws IOException {
        Path folder = Path.of(folderPath);

        return Files.list(folder)
                .filter(Files::isRegularFile)
                .map(Path::getFileName)
                .map(Path::toString)
                .collect(Collectors.toList());
    }
}
