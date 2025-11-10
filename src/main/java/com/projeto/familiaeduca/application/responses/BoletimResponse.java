package com.projeto.familiaeduca.application.responses;

import lombok.Getter;
import lombok.Setter;
import java.time.Year;
import java.util.UUID;

@Getter
@Setter
public class BoletimResponse {
    private UUID id;
    private String bimestre;
    private Year ano;
    private String observacoes;
    private AlunoResumeResponse aluno;
    private DiretorResumeResponse diretor;

    @Getter
    @Setter
    public static class AlunoResumeResponse {
        private int matricula;
        private String nome;
    }

    @Getter
    @Setter
    public static class DiretorResumeResponse {
        private UUID id;
        private String nome;
    }
}