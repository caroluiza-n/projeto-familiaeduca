package com.projeto.familiaeduca.application.responses;

import com.projeto.familiaeduca.domain.models.enums.StatusRenovacao;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.UUID;

/* Formato de resposta da classe RenovacaoMatricula que a API vai entregar */
@Getter
@Setter
public class RenovacaoMatriculaResponse {
    private UUID id;
    private int anoLetivo;
    private LocalDate data;
    private StatusRenovacao status;
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