package com.projeto.familiaeduca.application.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.UUID;

/* DTO para criação de Frequencia */
@Getter
@Setter
public class CreateFrequenciaRequest {
    @NotNull(message = "A data não pode ser nula.")
    @PastOrPresent(message = "A data da frequência não pode ser futura.")
    private LocalDate data;

    @NotNull(message = "O campo presente não pode ser nulo.")
    private Boolean presente;

    @NotNull(message = "A matrícula do aluno não pode ser nula.")
    private Integer matriculaAluno;

    @NotNull(message = "O id da turma não pode ser nulo.")
    private UUID idTurma;

    @NotNull(message = "O id do Professor não pode ser nulo.")
    private UUID idProfessor;
}