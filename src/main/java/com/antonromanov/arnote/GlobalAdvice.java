package com.antonromanov.arnote;

import com.antonromanov.arnote.sex.exceptions.UserNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalAdvice {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Response> handleException(UserNotFoundException e) {
        Response response = new Response("Произошел мощнейший пиздец!");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    private static class Response {
        private String message;
    }
}
