package com.projeto.familiaeduca.application.requests;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/* DTO para atualização de Calendario */
@Getter
@Setter
public class UpdateCalendarioRequest {
    private String titulo;
    private LocalDate dataEvento;
    private String descricao;
    private List<UUID> idProfessores;
    private List<UUID> idResponsaveis;
}