package com.projeto.familiaeduca.application.requests;

import lombok.Getter;
import lombok.Setter;

/* DTO para atualização de senha */
@Getter
@Setter
public class UpdatePasswordRequest {
    private String senhaAntiga;
    private String senhaNova;
}
