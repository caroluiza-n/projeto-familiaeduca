package com.projeto.familiaeduca.application.responses;

import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

/* Formato de resposta da classe Diretor que a API vai entregar */
@Getter
@Setter
public class DiretorResponse {
    private UUID id;
    private String nome;
    private String email;
    private String telefone;
    private final String funcao = "Diretor";
}