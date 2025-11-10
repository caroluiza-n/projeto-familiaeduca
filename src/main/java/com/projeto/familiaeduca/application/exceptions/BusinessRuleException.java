package com.projeto.familiaeduca.application.exceptions;

/* Função personalizada para caso o status seja Bad Request */
public class BusinessRuleException extends RuntimeException {
    public BusinessRuleException(String message) {
        super(message);
    }
}
