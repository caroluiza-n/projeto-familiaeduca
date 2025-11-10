package com.projeto.familiaeduca.application.exceptions;

/* Função personalizada para caso o status seja Conflict */
public class DataIntegrityException extends RuntimeException {
    public DataIntegrityException(String message) {
        super(message);
    }
}
