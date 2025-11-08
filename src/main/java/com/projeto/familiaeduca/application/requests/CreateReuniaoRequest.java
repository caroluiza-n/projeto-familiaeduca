package com.projeto.familiaeduca.application.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class CreateReuniaoRequest {

    @NotNull(message = "A data da reunião não pode ser nula.")
    @FutureOrPresent(message = "A data da reunião deve ser hoje ou uma data futura.")
    private LocalDate data;

    @NotBlank(message = "O motivo da reunião não pode estar em branco.")
    private String motivo;

    @NotNull(message = "O ID do Responsável não pode ser nulo.")
    private UUID idResponsavel;
}