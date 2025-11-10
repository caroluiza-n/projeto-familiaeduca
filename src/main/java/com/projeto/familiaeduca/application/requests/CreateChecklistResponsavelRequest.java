package com.projeto.familiaeduca.application.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.UUID;

/* DTO para criação de Checklist para Responsavel */
@Getter
@Setter
public class CreateChecklistResponsavelRequest {
    @NotNull(message = "A data do Checklist não pode ser nula.")
    @PastOrPresent(message = "A data do Checklist não pode ser futura.")
    private LocalDate dataChecklist;

    @NotBlank(message = "Os itens verificados não podem estar em branco.")
    private String itensVerificados;

    @NotNull(message = "A matrícula do aluno não pode ser nula.")
    private Integer matriculaAluno;

    @NotNull(message = "O id do Responsável não pode ser nulo.")
    private UUID idResponsavel;
}