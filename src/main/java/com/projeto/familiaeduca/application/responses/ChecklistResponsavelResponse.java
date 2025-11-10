package com.projeto.familiaeduca.application.responses;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.UUID;

/* Formato de resposta da classe ChecklistResponsavel que a API vai entregar */
@Getter
@Setter
public class ChecklistResponsavelResponse {
    private UUID id;
    private LocalDate dataChecklist;
    private String itensVerificados;
    private AlunoResumeResponse aluno;
    private ResponsavelResumeResponse responsavel;

    @Getter
    @Setter
    public static class AlunoResumeResponse {
        private int matricula;
        private String nome;
    }

    @Getter
    @Setter
    public static class ResponsavelResumeResponse {
        private UUID id;
        private String nome;
    }
}