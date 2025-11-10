package com.projeto.familiaeduca.application.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.UUID;

/* DTO para criação de Nota */
@Getter
@Setter
public class CreateNotaRequest {
    @NotNull(message = "O id da disciplina é obrigatório.")
    private UUID idDisciplina;

    @NotNull(message = "O id do boletim é obrigatório.")
    private UUID idBoletim;

    @NotBlank(message = "A nota não pode estar em branco.")
    private String nota;

    @NotNull(message = "A data de avaliação não pode ser nula.")
    @PastOrPresent(message = "A data de avaliação não pode ser futura.")
    private LocalDate dataAvaliacao;}