package com.projeto.familiaeduca.api.controllers;

import com.projeto.familiaeduca.application.exceptions.response.ApiResponse;
import com.projeto.familiaeduca.application.requests.TurmaRequest;
import com.projeto.familiaeduca.application.responses.TurmaResponse;
import com.projeto.familiaeduca.application.responses.TurmaResumeResponse;
import com.projeto.familiaeduca.application.services.TurmaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/turmas")
public class TurmaController {

    /* Dependência para chamar funções de Service */
    private final TurmaService turmaService;

    public TurmaController(TurmaService turmaService) {
        this.turmaService = turmaService;
    }

    /* Endpoint para inserir uma turma */
    @PostMapping
    @PreAuthorize("hasRole('DIRETOR')")
    public ResponseEntity<TurmaResponse> create(@Valid @RequestBody TurmaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(turmaService.create(request));
    }

    /* Endpoint para buscar a lista de todas as turmas cadastradas */
    @GetMapping
    @PreAuthorize("hasAnyRole('DIRETOR', 'PROFESSOR')")
    public ResponseEntity<List<TurmaResumeResponse>> getAll() {
        return ResponseEntity.ok(turmaService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurmaResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(turmaService.getById(id));
    }

    /* Endpoint para a atualizar informações de uma turma */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('DIRETOR')")
    public ResponseEntity<TurmaResponse> update(@PathVariable UUID id, @RequestBody TurmaRequest request) {
        return ResponseEntity.ok(turmaService.update(id, request));
    }

    /* Endpoint para a exclusão de uma turma */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('DIRETOR')")
    public ResponseEntity<ApiResponse> delete(@PathVariable UUID id) {
        turmaService.delete(id);
        return ResponseEntity.ok(new ApiResponse("Turma com id " + id + " deletada com sucesso."));
    }
}
