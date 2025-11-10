package com.projeto.familiaeduca.application.requests;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class UpdateChecklistResponsavelRequest {
    private LocalDate dataChecklist;
    private String itensVerificados;
}
