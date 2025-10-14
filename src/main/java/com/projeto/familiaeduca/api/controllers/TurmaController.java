package com.projeto.familiaeduca.api.controllers;

import com.projeto.familiaeduca.application.exceptions.response.ApiResponse;
import com.projeto.familiaeduca.application.requests.TurmaRequest;
import com.projeto.familiaeduca.application.responses.TurmaResponse;
import com.projeto.familiaeduca.application.responses.TurmaResumeResponse;
import com.projeto.familiaeduca.application.services.TurmaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/turmas")
public class TurmaController {

    private final TurmaService turmaService;

    public TurmaController(TurmaService turmaService) {
        this.turmaService = turmaService;
    }

    @PostMapping
    @PreAuthorize("hasRole('DIRETOR')")
    public ResponseEntity<TurmaResponse> create(@RequestBody TurmaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(turmaService.create(request));
    }

    @GetMapping
    @PreAuthorize("hasRole('DIRETOR')")
    public ResponseEntity<List<TurmaResumeResponse>> getAll() {
        return ResponseEntity.ok(turmaService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurmaResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(turmaService.getById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('DIRETOR')")
    public ResponseEntity<TurmaResponse> update(@PathVariable UUID id, @RequestBody TurmaRequest request) {
        return ResponseEntity.ok(turmaService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('DIRETOR')")
    public ResponseEntity<ApiResponse> delete(@PathVariable UUID id) {
        turmaService.delete(id);
        return ResponseEntity.ok(new ApiResponse("Turma com id " + id + " deletada com sucesso."));
    }
}
