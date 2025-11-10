package com.projeto.familiaeduca.application.requests;

import lombok.Getter;
import lombok.Setter;

/* DTO para atualização de Responsavel */
@Getter
@Setter
public class UpdateResponsavelRequest {
    private String nome;
    private String email;
    private String telefone;
    private String endereco;
}
