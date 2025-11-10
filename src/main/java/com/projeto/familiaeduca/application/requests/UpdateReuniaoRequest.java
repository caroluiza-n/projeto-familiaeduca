package com.projeto.familiaeduca.application.requests;

import com.projeto.familiaeduca.domain.models.enums.StatusReuniao;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

/* DTO para atualização de Reuniao */
@Getter
@Setter
public class UpdateReuniaoRequest {
    private LocalDate data;
    private String motivo;
    private StatusReuniao status;
}
