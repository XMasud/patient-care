package com.pm.patient_service.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException exception) {

        Map<String, String> fieldErrors = new HashMap<>();

        exception.getBindingResult().getFieldErrors().forEach(
                fieldError -> fieldErrors.put(fieldError.getField(), fieldError.getDefaultMessage()));

        return ResponseEntity.badRequest().body(fieldErrors);
    }

    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<Map<String, String>> handleEmailExistsException(EmailAlreadyExistException emailException) {

        log.warn("Email address already exists {}",emailException.getMessage());

        Map<String, String> errors = new HashMap<>();
        errors.put("message", "Email address already exists");

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<Map<String,String>> handlePatientNotFoundException(PatientNotFoundException patientException){
        log.warn("Patient not found with id: {}",patientException.getMessage());

        Map<String, String> errors = new HashMap<>();
        errors.put("message", "Patient not found");

        return ResponseEntity.badRequest().body(errors);
    }
}
