package com.projeto.familiaeduca.application.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.UUID;

/* DTO para criação de Checklist para Professor */
@Getter
@Setter
public class CreateChecklistProfessorRequest {
    @NotNull(message = "A data do Checklist não pode ser nula.")
    @PastOrPresent(message = "A data do Checklist não pode ser futura.")
    private LocalDate dataChecklist;
    private String observacoes;

    @NotNull(message = "A matrícula do aluno não pode ser nula.")
    private Integer matriculaAluno;

    @NotNull(message = "O id do Professor não pode ser nulo.")
    private UUID idProfessor;
}