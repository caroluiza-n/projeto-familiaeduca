package com.projeto.familiaeduca.application.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
public class TurmaRequest {
    @NotBlank(message = "O nome da turma não pode estar em branco.")
    private String nome;

    private UUID idProfessor;
}
