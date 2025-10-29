package com.projeto.familiaeduca.api.controllers;

import com.projeto.familiaeduca.application.requests.CreateProfessorRequest;
import com.projeto.familiaeduca.application.requests.UpdateProfessorRequest;
import com.projeto.familiaeduca.application.responses.UsuarioResponse;
import com.projeto.familiaeduca.application.services.ProfessorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/professores")
public class ProfessorController {

    private final ProfessorService professorService;

    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @PostMapping
    public ResponseEntity<UsuarioResponse> create(@Valid @RequestBody CreateProfessorRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(professorService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> update(@PathVariable UUID id, @RequestBody UpdateProfessorRequest request) {
        return ResponseEntity.ok(professorService.update(id, request));
    }
}
