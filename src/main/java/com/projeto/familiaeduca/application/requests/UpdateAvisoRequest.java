package com.projeto.familiaeduca.application.requests;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class UpdateAvisoRequest {

    @Size(max = 100, message = "O título deve ter no máximo 100 caracteres.")
    private String titulo;

    private String mensagem;

    private List<UUID> idsProfessores;

    private List<UUID> idsResponsaveis;
}