package com.restaurant.service.handler;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.restaurant.service.constants.Constants;
import com.restaurant.service.exception.ValidationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = Logger.getLogger(GlobalExceptionHandler.class.getName());

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(ValidationException ex) {
        LOGGER.log(Level.SEVERE, "Exception: ", ex);
        ErrorResponse errorResponse = new ErrorResponse(Constants.UNPROCESSABLE_ENTITY_ERROR_MESSAGE, ex.getMessage());
        return ResponseEntity.unprocessableEntity().body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        LOGGER.log(Level.SEVERE, "Exception: ", ex);
        ErrorResponse errorResponse = new ErrorResponse(Constants.INTERNAL_SERVER_ERROR_MESSAGE,
                "Error occurred while processing the request");
        return ResponseEntity.internalServerError().body(errorResponse);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, HttpMediaTypeNotSupportedException.class})
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableAndMediaTypeException(Exception ex) {
        LOGGER.log(Level.SEVERE, "Exception: ", ex);
        ErrorResponse errorResponse = new ErrorResponse(Constants.BAD_REQUEST_ERROR_MESSAGE, Constants.INVALID_PAYLOAD);
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        LOGGER.log(Level.SEVERE, "Exception: ", ex);
        ErrorResponse errorResponse = new ErrorResponse(Constants.BAD_REQUEST_ERROR_MESSAGE, ex.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

}
