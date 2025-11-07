package com.projeto.familiaeduca.application.responses;

import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
public class ResponsavelResponse {
    private UUID id;
    private String nome;
    private String email;
    private String telefone;
    private final String funcao = "Responsavel";

    private String endereco;
    private List<AlunoResumeResponse> alunos;

    @Getter @Setter
    public static class AlunoResumeResponse {
        private int matricula;
        private String nome;
    }
}