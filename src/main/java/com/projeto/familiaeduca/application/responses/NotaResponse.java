package com.projeto.familiaeduca.application.responses;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.time.Year;
import java.util.UUID;

/* Formato de resposta da classe Nota que a API vai entregar */
@Getter
@Setter
public class NotaResponse {
    private UUID id;
    private String disciplina;
    private String nota;
    private LocalDate dataAvaliacao;
    private AlunoResumeResponse aluno;
    private BoletimResumeResponse boletim;

    @Getter
    @Setter
    public static class AlunoResumeResponse {
        private int matricula;
        private String nome;
    }

    @Getter
    @Setter
    public static class BoletimResumeResponse {
        private UUID id;
        private String bimestre;
        private Year ano;
    }
}