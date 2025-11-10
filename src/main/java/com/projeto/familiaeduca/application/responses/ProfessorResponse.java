package com.projeto.familiaeduca.application.responses;

import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

/* Formato de resposta da classe Professor que a API vai entregar */
@Getter
@Setter
public class ProfessorResponse {
    private UUID id;
    private String nome;
    private String email;
    private String telefone;
    private final String funcao = "Professor";

    private TurmaResumeResponse turma;

    @Getter
    @Setter
    public static class TurmaResumeResponse {
        private UUID id;
        private String nome;
    }
}