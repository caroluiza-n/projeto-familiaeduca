package com.projeto.familiaeduca.api.controllers;

import com.projeto.familiaeduca.application.exceptions.response.ApiResponse;
import com.projeto.familiaeduca.application.requests.CreateNotaRequest;
import com.projeto.familiaeduca.application.requests.UpdateNotaRequest;
import com.projeto.familiaeduca.application.responses.NotaResponse;
import com.projeto.familiaeduca.application.services.NotaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/notas")
public class ControllerNota{

    private final NotaService notaService;

    public NotaController(NotaService notaService) {
        this.notaService = notaService;
    }


    @PostMapping
    @PreAuthorize("hasAnyRole('DIRETOR', 'PROFESSOR')")
    public ResponseEntity<NotaResponse> create(@Valid @RequestBody CreateNotaRequest request) {
        NotaResponse nota = notaService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(nota);
    }


    @GetMapping
    @PreAuthorize("hasRole('DIRETOR')")
    public ResponseEntity<List<NotaResponse>> getAll() {
        List<NotaResponse> notas = notaService.getAll();
        return ResponseEntity.ok(notas);
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('DIRETOR', 'PROFESSOR', 'RESPONSAVEL')")
    public ResponseEntity<NotaResponse> getById(@PathVariable UUID id) {
        NotaResponse nota = notaService.getById(id);
        return ResponseEntity.ok(nota);
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('DIRETOR', 'PROFESSOR')")
    public ResponseEntity<NotaResponse> update(@PathVariable UUID id, @RequestBody UpdateNotaRequest request) {
        NotaResponse nota = notaService.update(id, request);
        return ResponseEntity.ok(nota);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('DIRETOR')")
    public ResponseEntity<ApiResponse> delete(@PathVariable UUID id) {
        notaService.delete(id);
        ApiResponse resposta = new ApiResponse("Registro de Nota com ID " + id + " deletado com sucesso.");
        return ResponseEntity.ok(resposta);
    }
}