package com.projeto.familiaeduca.api.controllers;

import com.projeto.familiaeduca.application.exceptions.response.ApiResponse;
import com.projeto.familiaeduca.application.requests.CreateAvisoRequest;
import com.projeto.familiaeduca.application.requests.UpdateAvisoRequest;
import com.projeto.familiaeduca.application.responses.AvisoResponse;
import com.projeto.familiaeduca.application.services.AvisoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

public class AvisoController {

    /* Dependência para chamar funções de Service */
    private final AvisoService avisoService;

    public AvisoController(AvisoService avisoService) {
        this.avisoService = avisoService;
    }

    /* Endpoint para inserir um Aviso */
    @PostMapping
    @PreAuthorize("hasRole('DIRETOR')") /* Quem pode fazer é o Diretor */
    public ResponseEntity<AvisoResponse> create(@Valid @RequestBody CreateAvisoRequest request) {
        AvisoResponse aviso = avisoService.create(request); /* Chama a função que cria */
        return ResponseEntity.status(HttpStatus.CREATED).body(aviso); /* Cria o Aviso e retorna as informações dele */
    }

    /* Endpoint para buscar a lista de todos os Avisos cadastrados */
    @GetMapping
    public ResponseEntity<List<AvisoResponse>> getAll() {
        List<AvisoResponse> avisos = avisoService.getAll(); /* Chama a função que faz o GET */
        return ResponseEntity.ok(avisos); /* Retorna a lista de avisos */
    }

    /* Endpoint para buscar o Aviso pelo id */
    @GetMapping("/{id}")
    public ResponseEntity<AvisoResponse> getById(@PathVariable UUID id) {
        AvisoResponse aviso = avisoService.getById(id); /* Chama a função que faz o GET */
        return ResponseEntity.ok(aviso); /* Retorna o Aviso */
    }

    /* Endpoint para a atualizar informações de um Aviso */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('DIRETOR')") /* Quem pode fazer é o Diretor */
    public ResponseEntity<AvisoResponse> update(@PathVariable UUID id, @RequestBody UpdateAvisoRequest request) {
        AvisoResponse aviso = avisoService.update(id, request); /* Chama a função que faz a atualização */
        return ResponseEntity.ok(aviso); /* Retorna o Aviso com as informações atualizadas */
    }

    /* Endpoint para a exclusão de um Aviso*/
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('DIRETOR')") /* Quem pode fazer é o Diretor */
    public ResponseEntity<ApiResponse> delete(@PathVariable UUID id) {
        avisoService.delete(id); /* Chama a função que faz a exclusão */
        ApiResponse resposta = new ApiResponse("Aviso com id " + id + " deletado com sucesso.");
        return ResponseEntity.ok(resposta); /* Retorna a resposta acima */
    }
}
