package com.projeto.familiaeduca.application.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateResponsavelRequest {
    private String nome;
    private String email;
    private String telefone;
    private String endereco;
}
