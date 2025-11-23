package com.projeto.familiaeduca.application.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponsavelResumeResponse {
    private UUID id;
    private String nome;
    private String email;
}