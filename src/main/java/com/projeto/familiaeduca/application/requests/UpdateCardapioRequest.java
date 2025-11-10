package com.projeto.familiaeduca.application.requests;

import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class UpdateCardapioRequest {
    private String descricao;
    private List<UUID> idProfessores;
    private List<UUID> idResponsaveis;
}