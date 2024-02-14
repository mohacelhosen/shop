package com.store.shop.audio.controller;

import com.store.shop.audio.service.AudioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;

@RestController
@RequestMapping("/api")
public class AudioController {

    @Autowired
    private AudioService audioService;

    @GetMapping("/audio/{audioName}")
    public ResponseEntity<byte[]> streamAudio(
            @PathVariable String audioName,
            @RequestHeader(value = "Range", required = false) String range
    ) throws IOException {
        // Retrieve the entire audio file in the desired format (FLAC, Opus, etc.)
        byte[] audioData = audioService.retrieveFileName(audioName);

        // If Range header is present, handle partial content
        if (range != null && range.startsWith("bytes=")) {
            String[] rangeValues = range.substring(6).split("-");
            long start = Long.parseLong(rangeValues[0]);
            long end = (rangeValues.length > 1) ? Long.parseLong(rangeValues[1]) : audioData.length - 1;

            // Adjust end if it exceeds the available data length
            end = Math.min(end, audioData.length - 1);

            long contentLength = end - start + 1;

            // Set the Content-Range header to inform the client about the range being sent
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.valueOf("audio/mpeg"));
            headers.setContentLength(contentLength);
            headers.set("Content-Range", "bytes " + start + "-" + end + "/" + audioData.length);

            // Return partial content with status code 206 (Partial Content)
            return new ResponseEntity<>(Arrays.copyOfRange(audioData, (int) start, (int) end + 1), headers, HttpStatus.PARTIAL_CONTENT);
        } else {
            // If Range header is not present, return the entire content
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.valueOf("audio/mpeg"));
            headers.setContentLength(audioData.length);
            return new ResponseEntity<>(audioData, headers, HttpStatus.OK);
        }
    }
}