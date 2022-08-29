package com.antonromanov.arnote;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// @ControllerAdvice
public class GlobalAdvice {

    /*@ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleAllExceptions(RuntimeException e) {*/

       /* if (e.getClass().isAssignableFrom(UserNotFoundException.class)) {
            UserNotFoundException exceptionClass = (UserNotFoundException) e.getClass().cast(UserNotFoundException.class);
            return new ResponseEntity<>(gb, HttpStatus.BAD_REQUEST);
        }*/
        /*return new ResponseEntity<>(GlobalResponse.builder()
                .status(ResponseStatus.builder()
                        .success(false)
                        .description(e.getMessage())
                        .build())
                .build(), HttpStatus.BAD_REQUEST);*/

     /*   return new ResponseEntity<>("1", HttpStatus.BAD_REQUEST);
    }*/
}
