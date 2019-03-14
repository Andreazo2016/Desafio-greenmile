package com.greenmile.desafio.handler;

import com.greenmile.desafio.error.UserErroDetails;
import com.greenmile.desafio.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(UserNotFoundException userNotFoundException) {
        UserErroDetails userErroDetails = new UserErroDetails();
        userErroDetails.setStatus(HttpStatus.NOT_FOUND.value());
        userErroDetails.setTitle("User not found");
        userErroDetails.setDetail(userNotFoundException.getMessage());
        userErroDetails.setDeveloperMessage(userNotFoundException.getClass().getName());
        userErroDetails.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(userErroDetails, HttpStatus.NOT_FOUND);
    }
}
