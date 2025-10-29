package com.projeto.familiaeduca.application.responses;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class MedicacaoResponse {
    private UUID id;
    private String medicamento;
    private String dosagem;
    private String observacoes;
    private LocalDateTime dataAplicacao;
    private AlunoResumeResponse aluno;
    private ProfessorResumeResponse professor;

    @Getter
    @Setter
    public static class AlunoResumeResponse {
        private int matricula;
        private String nome;
    }

    @Getter
    @Setter
    public static class ProfessorResumeResponse {
        private UUID id;
        private String nome;
    }
}
