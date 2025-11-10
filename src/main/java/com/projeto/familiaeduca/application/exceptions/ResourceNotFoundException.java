package com.projeto.familiaeduca.application.exceptions;

/* Função personalizada para caso o status seja Not Found */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
