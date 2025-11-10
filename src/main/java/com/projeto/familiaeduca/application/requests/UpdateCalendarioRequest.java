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

    private String titulo;
    private LocalDate dataEvento;
    private String descricao;
    private List<UUID> idProfessores;
    private List<UUID> idResponsaveis;
}