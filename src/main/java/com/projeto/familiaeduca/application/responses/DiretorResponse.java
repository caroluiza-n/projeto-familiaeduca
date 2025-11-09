package com.projeto.familiaeduca.application.responses;

import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
public class DiretorResponse {
    private UUID id;
    private String nome;
    private String email;
    private String telefone;
    private final String funcao = "Diretor";
}