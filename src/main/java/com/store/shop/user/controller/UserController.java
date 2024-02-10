package com.store.shop.user.controller;

import com.store.shop.exception.DuplicateKeyException;
import com.store.shop.user.model.User;
import com.store.shop.user.service.UserService;
import com.store.shop.util.ApiResponse;
import com.store.shop.util.Common;
import com.store.shop.util.Error;
import com.store.shop.util.Success;
import jakarta.el.PropertyNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/user")
    public ResponseEntity<?> saveUser(@RequestBody User user, HttpServletRequest request) {
        String endpoint = request.getRequestURI();
        String requestId = Common.getRequestId();
        String timeStamp = Common.getTimeStamp();

        try {
            User data = userService.saveUser(user);
            Success<User> successResponse = ApiResponse.success(timeStamp, requestId, data, endpoint, HttpStatus.CREATED.value());
            return new ResponseEntity<>(successResponse, HttpStatus.CREATED);
        } catch (DuplicateKeyException e) {
            Error errorResponse = ApiResponse.error(timeStamp, requestId, endpoint, HttpStatus.CONFLICT.value(), e.getMessage());
            logger.info("Error saving user with ID: {}", user.getId());
            return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Integer userId,
                                         @RequestHeader("Authorization") String authorization,
                                         @RequestHeader("Accept-Language") String acceptLanguage,
                                         HttpServletRequest request) {
        String endpoint = request.getRequestURI();
        String requestId = Common.getRequestId();
        String timeStamp = Common.getTimeStamp();

        HttpHeaders headers = new HttpHeaders();

        // Standard headers
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setCacheControl("no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        try {
            // Your existing code for fetching the user
            User dbUser = userService.findUserById(userId);

            // Create success response with headers
            Success<User> successResponse = ApiResponse.success(timeStamp, requestId, dbUser, endpoint, HttpStatus.OK.value());
            return new ResponseEntity<>(successResponse, headers, HttpStatus.OK);
        } catch (PropertyNotFoundException notFoundException) {
            // Your existing code for handling not found exception
            Error errorResponse = ApiResponse.error(timeStamp, requestId, endpoint, HttpStatus.NOT_FOUND.value(), notFoundException.getMessage());

            // Create error response with headers
            return new ResponseEntity<>(errorResponse, headers, HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/user")
    public ResponseEntity<?> filterUser(
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) Integer age,
            @RequestParam(required = false) String blood,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String zipCode,
            @RequestParam(required = false) String sort,
            HttpServletRequest request
    ){
        String endpoint = request.getRequestURI();
        String requestId = Common.getRequestId();
        String timeStamp = Common.getTimeStamp();
        try{
            List<User> filteredUsers= userService.filterAndSorting(gender,age,blood,country,state,city,zipCode,sort);
            Success<List<User>> successResponse = ApiResponse.success(timeStamp, requestId, filteredUsers, endpoint, HttpStatus.OK.value());
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        }catch (Exception e) {
            Error errorResponse = ApiResponse.error(timeStamp, requestId, endpoint, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
            logger.error("Error filtering users: {}", e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

