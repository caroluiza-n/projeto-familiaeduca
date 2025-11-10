package com.projeto.familiaeduca.api.controllers;

import com.projeto.familiaeduca.application.exceptions.response.ApiResponse;
import com.projeto.familiaeduca.application.requests.CreateAlunoRequest;
import com.projeto.familiaeduca.application.requests.UpdateAlunoRequest;
import com.projeto.familiaeduca.application.responses.AlunoResponse;
import com.projeto.familiaeduca.application.services.AlunoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    /* Dependência para chamar funções de Service */
    private final AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    /* Endpoint para cadastrar um aluno */
    @PostMapping
    @PreAuthorize("hasRole('DIRETOR')") /* Quem pode fazer é o diretor */
    public ResponseEntity<AlunoResponse> create(@Valid @RequestBody CreateAlunoRequest request) {
        AlunoResponse aluno = alunoService.create(request); /* Chama a função que cria */
        return ResponseEntity.status(HttpStatus.CREATED).body(aluno); /* Cria o aluno e retorna as informações dele */
    }

    /* Endpoint para buscar a lista de todos os alunos cadastrados */
    @GetMapping
    @PreAuthorize("hasRole('DIRETOR')") /* Quem pode fazer é o diretor */
    public ResponseEntity<List<AlunoResponse>> getAll() {
        List<AlunoResponse> alunos = alunoService.getAll(); /* Chama a função que faz o GET */
        return ResponseEntity.ok(alunos); /* Retorna a lista de alunos */
    }

    /* Endpoint para buscar o aluno pela matrícula */
    @GetMapping("/{matricula}")
    public ResponseEntity<AlunoResponse> getById(@PathVariable int matricula) {
        AlunoResponse aluno = alunoService.getById(matricula); /* Chama a função que faz o GET */
        return ResponseEntity.ok(aluno); /* Retorna o aluno */
    }

    /* Endpoint para a atualizar informações de um aluno */
    @PutMapping("/{matricula}")
    @PreAuthorize("hasRole('DIRETOR')") /* Quem pode fazer é o diretor */
    public ResponseEntity<AlunoResponse> update(@PathVariable int matricula, @RequestBody UpdateAlunoRequest request) {
        AlunoResponse aluno = alunoService.update(matricula, request); /* Chama a função que faz a atualização */
        return ResponseEntity.ok(aluno); /* Retorna o aluno com as informações atualizadas */
    }

    /* Endpoint para a exclusão de um aluno */
    @DeleteMapping("/{matricula}")
    @PreAuthorize("hasRole('DIRETOR')") /* Quem pode fazer é o diretor */
    public ResponseEntity<ApiResponse> delete(@PathVariable int matricula) {
        alunoService.delete(matricula); /* Chama a função que faz a exclusão */
        ApiResponse resposta = new ApiResponse("Aluno com matrícula " + matricula + " deletado com sucesso.");
        return ResponseEntity.ok(resposta); /* Retorna a resposta acima */
    }
}
