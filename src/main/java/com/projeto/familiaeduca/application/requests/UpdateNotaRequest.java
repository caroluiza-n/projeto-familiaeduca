package com.projeto.familiaeduca.application.requests;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

/* DTO para atualização de Nota */
@Getter
@Setter
public class UpdateNotaRequest {
    private String disciplina;
    private String nota;
    private LocalDate dataAvaliacao;
}