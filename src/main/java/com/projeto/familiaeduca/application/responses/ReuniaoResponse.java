package com.projeto.familiaeduca.application.responses;

import com.projeto.familiaeduca.domain.models.enums.StatusReuniao;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.UUID;

/* Formato de resposta da classe Reuniao que a API vai entregar */
@Getter
@Setter
public class ReuniaoResponse {
    private UUID id;
    private LocalDate data;
    private String motivo;
    private StatusReuniao status;
    private ResponsavelResumeResponse responsavel;

    @Getter
    @Setter
    public static class ResponsavelResumeResponse {
        private UUID id;
        private String nome;
    }
}