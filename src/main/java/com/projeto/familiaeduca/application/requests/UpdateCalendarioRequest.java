package com.projeto.familiaeduca.application.requests;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class UpdateCalendarioRequest {

    @Size(max = 100, message = "O título deve ter no máximo 100 caracteres.")
    private String titulo;

    @FutureOrPresent(message = "A data do evento deve ser hoje ou uma data futura.")
    private LocalDate dataEvento;

    private String descricao;

    private List<UUID> idsProfessores;

    private List<UUID> idsResponsaveis;
}