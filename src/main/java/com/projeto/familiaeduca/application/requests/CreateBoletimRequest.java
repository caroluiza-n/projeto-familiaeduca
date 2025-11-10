package com.projeto.familiaeduca.application.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

/* DTO para criação de Boletim */
@Getter
@Setter
public class CreateBoletimRequest {
    @NotBlank(message = "O bimestre não pode estar em branco.")
    private String bimestre;

    @NotNull(message = "O ano não pode ser nulo.")
    @Min(value = 2000, message = "O ano deve ser válido.")
    private Integer ano;
    private String observacoes;

    @NotNull(message = "A matrícula do aluno não pode ser nula.")
    private Integer matriculaAluno;

    @NotNull(message = "O id do Diretor não pode ser nulo.")
    private UUID idDiretor;
}