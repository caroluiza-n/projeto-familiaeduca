package com.projeto.familiaeduca.application.responses;

import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

/* Formato de resposta da classe Usuario que a API vai entregar */
@Getter
@Setter
public class UsuarioResponse {
    private UUID id;
    private String nome;
    private String email;
    private String telefone;
    private String funcao;
}
