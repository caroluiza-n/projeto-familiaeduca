package com.projeto.familiaeduca.application.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.UUID;

/* DTO para criação de Cardapio */
@Getter
@Setter
public class CreateCardapioRequest {
    @NotBlank(message = "A descrição do cardápio não pode estar em branco.")
    private String descricao;

    @NotNull(message = "O id do Diretor não pode ser nulo.")
    private UUID idDiretor;
    private List<UUID> idProfessores;
    private List<UUID> idResponsaveis;
}