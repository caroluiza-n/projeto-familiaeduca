package com.projeto.familiaeduca.application.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class CreateJustificativaRequest {

    @NotNull(message = "A data da falta não pode ser nula.")
    @PastOrPresent(message = "A data da falta não pode ser futura.")
    private LocalDate dataFalta;

    @NotBlank(message = "O motivo da justificativa não pode estar em branco.")
    private String motivo;

    private String anexo;

    @NotNull(message = "A matrícula do aluno não pode ser nula.")
    private Integer matriculaAluno;

    @NotNull(message = "O ID do Responsável não pode ser nulo.")
    private UUID idResponsavel;
}