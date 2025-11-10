package com.projeto.familiaeduca.application.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/* DTO para criação de Medicacao */
@Getter
@Setter
public class CreateMedicacaoRequest {
    @NotBlank(message = "O nome do medicamento não pode estar em branco.")
    private String medicamento;
    private String dosagem;
    private String observacoes;
    private int matricula;
}
