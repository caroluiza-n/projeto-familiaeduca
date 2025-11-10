package com.projeto.familiaeduca.application.requests;

import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

/* DTO para atualização de Boletim */
@Getter
@Setter
public class UpdateBoletimRequest {
    private String bimestre;
    private Integer ano;
    private String observacoes;
    private Integer matriculaAluno;
    private UUID idDiretor;
}
