package com.projeto.familiaeduca.application.requests;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CreateCalendarioRequest {

    @NotBlank(message = "O título do evento não pode estar em branco.")
    @Size(max = 100, message = "O título deve ter no máximo 100 caracteres.")
    private String titulo;

    @NotNull(message = "A data do evento não pode ser nula.")
    @FutureOrPresent(message = "A data do evento deve ser hoje ou uma data futura.")
    private LocalDate dataEvento;

    @NotBlank(message = "A descrição do evento não pode estar em branco.")
    private String descricao;

    @NotNull(message = "O ID do Diretor não pode ser nulo.")
    private UUID idDiretor;

    private List<UUID> idsProfessores;

    private List<UUID> idsResponsaveis;
}