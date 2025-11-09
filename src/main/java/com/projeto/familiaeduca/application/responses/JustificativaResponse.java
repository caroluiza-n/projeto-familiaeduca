package com.projeto.familiaeduca.application.responses;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class JustificativaResponse {
    private UUID id;
    private LocalDate data;
    private String motivo;
    private String anexo;
    private AlunoResumeResponse aluno;
    private ResponsavelResumeResponse responsavel;

    @Getter @Setter
    public static class AlunoResumeResponse {
        private int matricula;
        private String nome;
    }

    @Getter @Setter
    public static class ResponsavelResumeResponse {
        private UUID id;
        private String nome;
    }
}