package com.projeto.familiaeduca.application.exceptions.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/* Classe para armazenar a resposta da API */
@Getter
@Setter
@AllArgsConstructor
public class ApiResponse {
    private String mensagem;
}
