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

    private final BoletimService boletimService;

    public BoletimController(BoletimService boletimService) {
        this.boletimService = boletimService;
    }

    @PostMapping
    @PreAuthorize("hasRole('DIRETOR')")
    public ResponseEntity<BoletimResponse> create(@Valid @RequestBody CreateBoletimRequest request) {
        BoletimResponse boletim = boletimService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(boletim);
    }

    @GetMapping
    @PreAuthorize("hasRole('DIRETOR')")
    public ResponseEntity<List<BoletimResponse>> getAll() {
        List<BoletimResponse> boletins = boletimService.getAll();
        return ResponseEntity.ok(boletins);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('DIRETOR', 'PROFESSOR', 'RESPONSAVEL')")
    public ResponseEntity<BoletimResponse> getById(@PathVariable UUID id) {
        BoletimResponse boletim = boletimService.getById(id);
        return ResponseEntity.ok(boletim);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('DIRETOR')")
    public ResponseEntity<BoletimResponse> update(@PathVariable UUID id, @RequestBody UpdateBoletimRequest request) {
        BoletimResponse boletim = boletimService.update(id, request);
        return ResponseEntity.ok(boletim);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('DIRETOR')")
    public ResponseEntity<ApiResponse> delete(@PathVariable UUID id) {
        boletimService.delete(id);
        ApiResponse resposta = new ApiResponse("Boletim com ID " + id + " deletado com sucesso.");
        return ResponseEntity.ok(resposta);
    }
}