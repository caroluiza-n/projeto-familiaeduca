package com.projeto.familiaeduca.application.responses;

import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
public class TurmaResumeResponse {
    private UUID id;
    private String nome;
    private String nomeProfessor;
}
