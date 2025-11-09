package com.projeto.familiaeduca.application.responses;

import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CalendarioResponse {
    private UUID id;
    private String descricao;
    private DiretorResumeResponse diretor;
    private List<ProfessorResumeResponse> professoresNotificados;
    private List<ResponsavelResumeResponse> responsaveisNotificados;

    @Getter @Setter
    public static class DiretorResumeResponse {
        private UUID id;
        private String nome;
    }

    @Getter @Setter
    public static class ProfessorResumeResponse {
        private UUID id;
        private String nome;
    }

    @Getter @Setter
    public static class ResponsavelResumeResponse {
        private UUID id;
        private String nome;
    }
}