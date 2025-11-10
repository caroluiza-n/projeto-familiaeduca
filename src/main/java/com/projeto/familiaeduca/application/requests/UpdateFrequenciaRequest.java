package com.projeto.familiaeduca.application.requests;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

/* DTO para atualização de Frequência */
@Getter
@Setter
public class UpdateFrequenciaRequest {
    private LocalDate data;
    private Boolean presente;
}
