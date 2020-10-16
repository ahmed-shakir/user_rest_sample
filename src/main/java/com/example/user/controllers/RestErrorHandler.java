package com.example.user.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Error/exception handler common for all Controllers
 *
 * @author Ahmed Shakir
 * @version 1.0
 * @since 2020-10-16
 */
//@ControllerAdvice
public class RestErrorHandler extends ResponseEntityExceptionHandler {

    //@ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleNotFound(ResponseStatusException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Could not find that...\nBest regards\nController Advice");
    }

    //@ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAccessDeniedException(Exception ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Access denied...\nBest regards\nController Advice");
    }
}