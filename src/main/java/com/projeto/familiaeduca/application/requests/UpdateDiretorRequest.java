package com.projeto.familiaeduca.application.requests;

import lombok.Getter;
import lombok.Setter;

/* DTO para atualização de Diretor */
@Getter
@Setter
public class UpdateDiretorRequest {
    private String nome;
    private String email;
    private String telefone;
}
