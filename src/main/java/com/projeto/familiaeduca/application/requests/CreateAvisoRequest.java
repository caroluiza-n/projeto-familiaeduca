package com.projeto.familiaeduca.application.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CreateAvisoRequest {

    @NotBlank(message = "O título não pode estar em branco.")
    @Size(max = 100, message = "O título deve ter no máximo 100 caracteres.")
    private String titulo;

    @NotBlank(message = "A mensagem não pode estar em branco.")
    private String mensagem;

    @NotNull(message = "O ID do Diretor não pode ser nulo.")
    private UUID idDiretor;

    private List<UUID> idsProfessores;


    private List<UUID> idsResponsaveis;
}