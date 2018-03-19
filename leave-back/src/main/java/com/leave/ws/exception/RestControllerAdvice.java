package com.leave.ws.exception;

import com.leave.ws.exception.RestException.RestError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestControllerAdvice {

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Exception> exceptionHandler(Exception ex) {
//        return new ResponseEntity<Exception>(ex, HttpStatus.BAD_REQUEST);
//    }

    @ExceptionHandler(RestException.class)
    public ResponseEntity<RestError> exceptionHandler(RestException ex) {
        return new ResponseEntity<RestError>(ex.geRestError(), ex.getStatus());
    }
}