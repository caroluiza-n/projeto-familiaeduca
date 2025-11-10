package com.projeto.familiaeduca.api.controllers;

import com.projeto.familiaeduca.application.exceptions.response.ApiResponse;
import com.projeto.familiaeduca.application.requests.CreateBoletimRequest;
import com.projeto.familiaeduca.application.requests.UpdateBoletimRequest;
import com.projeto.familiaeduca.application.responses.BoletimResponse;
import com.projeto.familiaeduca.application.services.BoletimService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/boletins")
public class BoletimController {

    /* Dependência para chamar funções de Service */
    private final BoletimService boletimService;

    public BoletimController(BoletimService boletimService) {
        this.boletimService = boletimService;
    }

    /* Endpoint para inserir um boletim */
    @PostMapping
    @PreAuthorize("hasRole('DIRETOR')") /* Quem pode fazer é o diretor */
    public ResponseEntity<BoletimResponse> create(@Valid @RequestBody CreateBoletimRequest request) {
        BoletimResponse boletim = boletimService.create(request); /* Chama a função que cria */
        return ResponseEntity.status(HttpStatus.CREATED).body(boletim); /* Cria o boletim e retorna as informações dele */
    }

    /* Endpoint para buscar a lista de todos os boletins cadastrados */
    @GetMapping
    @PreAuthorize("hasRole('DIRETOR')") /* Quem pode fazer é o diretor */
    public ResponseEntity<List<BoletimResponse>> getAll() {
        List<BoletimResponse> boletins = boletimService.getAll(); /* Chama a função que faz o GET */
        return ResponseEntity.ok(boletins); /* Retorna a lista de boletins */
    }

    /* Endpoint para buscar o boletim pelo id */
    @GetMapping("/{id}")
    public ResponseEntity<BoletimResponse> getById(@PathVariable UUID id) {
        BoletimResponse boletim = boletimService.getById(id); /* Chama a função que faz o GET */
        return ResponseEntity.ok(boletim); /* Retorna o boletim */
    }

    /* Endpoit para buscar boletins pela matrícula do aluno */
    @GetMapping("/aluno/{matricula}")
    public ResponseEntity<List<BoletimResponse>> findByAlunoMatricula(@PathVariable int matricula) {
        List<BoletimResponse> boletins = boletimService.getByAluno(matricula); /* Chama a função que faz o GET */
        return ResponseEntity.ok(boletins); /* Retorna a lista de boletins do aluno */
    }

    /* Endpoint para a atualizar informações de um boletim */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('DIRETOR')") /* Quem pode fazer é o diretor */
    public ResponseEntity<BoletimResponse> update(@PathVariable UUID id, @RequestBody UpdateBoletimRequest request) {
        BoletimResponse boletim = boletimService.update(id, request); /* Chama a função que faz a atualização */
        return ResponseEntity.ok(boletim); /* Retorna o boletim com as informações atualizadas */
    }

    /* Endpoint para a exclusão de um boletim */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('DIRETOR')") /* Quem pode fazer é o diretor */
    public ResponseEntity<ApiResponse> delete(@PathVariable UUID id) {
        boletimService.delete(id); /* Chama a função que faz a exclusão */
        ApiResponse resposta = new ApiResponse("Boletim com id " + id + " deletado com sucesso.");
        return ResponseEntity.ok(resposta); /* Retorna a resposta acima */
    }
}
