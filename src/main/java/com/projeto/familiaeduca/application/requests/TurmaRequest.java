package com.projeto.familiaeduca.application.requests;

import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
public class TurmaRequest {
    private String nome;
    private UUID idProfessor;
}
