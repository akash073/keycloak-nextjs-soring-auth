package com.dsi.demo.exception;

import com.dsi.demo.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlers {


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {

        HttpStatus status = HttpStatus.FORBIDDEN;

        String message = "Access role not found!";
        HttpStatus httpStatus  = HttpStatus.FORBIDDEN;
        ErrorResponse errorResponse = new ErrorResponse(httpStatus,message);

        return new ResponseEntity<>(errorResponse, status);
    }
}