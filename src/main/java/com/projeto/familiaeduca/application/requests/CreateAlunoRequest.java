package com.projeto.familiaeduca.application.requests;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class CreateAlunoRequest {
    private int matricula;
    private String nome;
    private LocalDate dataNascimento;
    private String laudo;
    private String alergias;
    private UUID idTurma;
    private UUID idResponsavel;
}
