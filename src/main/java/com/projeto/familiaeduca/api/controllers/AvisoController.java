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

@RestController
@RequestMapping("/avisos")
public class AvisoController {

    private final AvisoService avisoService;

    public AvisoController(AvisoService avisoService) {
        this.avisoService = avisoService;
    }

    @PostMapping
    @PreAuthorize("hasRole('DIRETOR')")
    public ResponseEntity<AvisoResponse> create(@Valid @RequestBody CreateAvisoRequest request) {
        AvisoResponse aviso = avisoService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(aviso);
    }


    @GetMapping
    public ResponseEntity<List<AvisoResponse>> getAll() {
        List<AvisoResponse> avisos = avisoService.getAll();
        return ResponseEntity.ok(avisos);
    }


    @GetMapping("/{id}")
    public ResponseEntity<AvisoResponse> getById(@PathVariable UUID id) {
        AvisoResponse aviso = avisoService.getById(id);
        return ResponseEntity.ok(aviso);
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasRole('DIRETOR')")
    public ResponseEntity<AvisoResponse> update(@PathVariable UUID id, @RequestBody UpdateAvisoRequest request) {
        AvisoResponse aviso = avisoService.update(id, request);
        return ResponseEntity.ok(aviso);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('DIRETOR')")
    public ResponseEntity<ApiResponse> delete(@PathVariable UUID id) {
        avisoService.delete(id);
        ApiResponse resposta = new ApiResponse("Aviso com ID " + id + " deletado com sucesso.");
        return ResponseEntity.ok(resposta);
    }
}
