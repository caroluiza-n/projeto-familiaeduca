package com.projeto.familiaeduca.application.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/* DTO para criação de Professor */
@Getter
@Setter
public class CreateProfessorRequest {
    @NotBlank(message = "O nome não pode estar em branco.")
    @Size(min = 5, message = "O nome deve ter pelo menos 5 caracteres.")
    private String nome;

    @NotBlank(message = "O e-mail não pode estar em branco.")
    @Email(message = "O formato do e-mail é inválido.")
    private String email;

    @NotBlank(message = "A senha não pode estar em branco.")
    @Size(min = 8, message = "A senha deve ter pelo menos 8 caracteres.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).+$", message = "A senha deve conter letras e números.")
    private String senha;

    @NotBlank(message = "O telefone não pode estar em branco.")
    private String telefone;
}
