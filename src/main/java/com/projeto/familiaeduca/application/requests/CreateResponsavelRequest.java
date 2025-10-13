package com.projeto.familiaeduca.application.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateResponsavelRequest {
    private String nome;
    private String email;
    private String senha;
    private String telefone;
    private String endereco;
}
