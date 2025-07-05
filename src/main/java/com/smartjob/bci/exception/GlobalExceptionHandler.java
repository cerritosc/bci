package com.smartjob.bci.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import com.smartjob.bci.dto.ErrorResponse;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("message", "campos no validos");
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.badRequest()
        		.contentType(MediaType.APPLICATION_JSON).body(errors);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgs(IllegalArgumentException ex) {
        return ResponseEntity.badRequest()
        		.contentType(MediaType.APPLICATION_JSON).body(Map.of("error", ex.getMessage()));
    }
    
    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<ErrorResponse> handleCorreoYaExiste(GlobalException ex, WebRequest request) {
        ErrorResponse errorResponse = ErrorResponse.builder()
        		.mensaje(ex.getMessage())
                .build();
        
        return ResponseEntity.status(HttpStatus.CONFLICT)
        		.contentType(MediaType.APPLICATION_JSON).body(errorResponse);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedError(Exception ex, WebRequest request) {
        ErrorResponse errorResponse = ErrorResponse.builder()
        		.mensaje("Ocurrió un error inesperado. Por favor intente más tarde.")
                .build();

        log.error("Error inesperado: ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        		.contentType(MediaType.APPLICATION_JSON).body(errorResponse);
    }

}
