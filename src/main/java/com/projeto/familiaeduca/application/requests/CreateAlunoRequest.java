package com.projeto.familiaeduca.application.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class CreateAlunoRequest {
    private int matricula;

    @NotBlank(message = "O nome não pode estar em branco.")
    @Size(min = 5, message = "O nome deve ter pelo menos 5 caracteres.")
    private String nome;

    @NotNull(message = "A data de nascimento não pode ser nula.")
    @Past(message = "A data de nascimento deve ser uma data no passado.")
    private LocalDate dataNascimento;
    private String laudo;
    private String alergias;
    private UUID idTurma;
    private UUID idResponsavel;
}
