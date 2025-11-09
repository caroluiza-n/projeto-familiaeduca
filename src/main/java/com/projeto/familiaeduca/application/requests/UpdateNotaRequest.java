package com.projeto.familiaeduca.application.requests;

import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class UpdateNotaRequest {

    private String disciplina;
    private String nota;

    @PastOrPresent(message = "A data de avaliação não pode ser futura.")
    private LocalDate dataAvaliacao;
}