package com.example.user.controllers;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

/**
 * Error/exception handler common for all Controllers
 *
 * @author Ahmed Shakir
 * @version 1.0
 * @since 2020-10-16
 */
@ControllerAdvice
public class RestErrorHandler {

    //@ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleNotFound(ResponseStatusException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Could not find that...\nBest regards\nController Advice");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<List<String>> handleValidationErrors(DataIntegrityViolationException e, WebRequest request) {
        var errors = new ArrayList<String>();
        /*e.getConstraintViolations().forEach(constraintViolation -> {
            errors.add(constraintViolation.getPropertyPath() + " : " + constraintViolation.getMessage());
        });*/
        return ResponseEntity.badRequest().body(List.of(e.getMessage()));
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<List<ObjectError>> handleValidationErrors(BindException e) {
        //return ResponseEntity.badRequest().body(e.getAllErrors());
        return ResponseEntity.badRequest().body(null);
    }
}