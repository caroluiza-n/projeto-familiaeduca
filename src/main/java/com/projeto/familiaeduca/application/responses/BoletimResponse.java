package com.projeto.familiaeduca.application.responses;

import lombok.Getter;
import lombok.Setter;
import java.time.Year;
import java.util.List;
import java.util.UUID;

/* Formato de resposta da classe Boletim que a API vai entregar */
@Getter
@Setter
public class BoletimResponse {
    private UUID id;
    private String bimestre;
    private Year ano;
    private String observacoes;
    private AlunoResumeResponse aluno;
    private DiretorResumeResponse diretor;
    private List<NotaResumeResponse> notas;

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

    @Getter
    @Setter
    public static class NotaResumeResponse {
        private UUID id;
        private String nomeDisciplina;
        private String nota;
    }
}