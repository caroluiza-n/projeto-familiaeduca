package com.projeto.familiaeduca.application.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePasswordRequest {
    private String senhaAntiga;
    private String senhaNova;
}
