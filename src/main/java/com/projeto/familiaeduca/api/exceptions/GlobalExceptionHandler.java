package com.projeto.familiaeduca.api.exceptions;

import com.projeto.familiaeduca.application.exceptions.BusinessRuleException;
import com.projeto.familiaeduca.application.exceptions.DataIntegrityException;
import com.projeto.familiaeduca.application.exceptions.ResourceNotFoundException;
import com.projeto.familiaeduca.application.exceptions.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ApiResponse errorResponse = new ApiResponse(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<ApiResponse> handleBusinessRuleException(BusinessRuleException ex) {
        ApiResponse errorResponse = new ApiResponse(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityException.class)
    public ResponseEntity<ApiResponse> handleDataIntegrityException(DataIntegrityException ex) {
        ApiResponse errorResponse = new ApiResponse(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse> handleAccessDeniedException(AccessDeniedException ex) {
        ApiResponse errorResponse = new ApiResponse("Acesso negado. Você não tem permissão para executar esta ação.");
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }
}
