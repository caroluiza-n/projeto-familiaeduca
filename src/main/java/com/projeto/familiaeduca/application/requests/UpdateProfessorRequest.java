package com.projeto.familiaeduca.application.requests;

import lombok.Getter;
import lombok.Setter;

/* DTO para atualização de Professor */
@Getter
@Setter
public class UpdateProfessorRequest {
    private String nome;
    private String email;
    private String telefone;
}
