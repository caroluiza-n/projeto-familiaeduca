package com.projeto.familiaeduca.api.controllers;

import com.projeto.familiaeduca.application.exceptions.response.ApiResponse;
import com.projeto.familiaeduca.application.requests.CreateFrequenciaRequest;
import com.projeto.familiaeduca.application.requests.UpdateFrequenciaRequest; // Será necessário criar este DTO
import com.projeto.familiaeduca.application.responses.FrequenciaResponse; // Será necessário criar este DTO
import com.projeto.familiaeduca.application.services.FrequenciaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/frequencias")
public class FrequenciaController {

    private final FrequenciaService frequenciaService;

    public FrequenciaController(FrequenciaService frequenciaService) {
        this.frequenciaService = frequenciaService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('DIRETOR', 'PROFESSOR')")
    public ResponseEntity<FrequenciaResponse> create(@Valid @RequestBody CreateFrequenciaRequest request) {
        FrequenciaResponse frequencia = frequenciaService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(frequencia);
    }


    @GetMapping
    @PreAuthorize("hasRole('DIRETOR')")
    public ResponseEntity<List<FrequenciaResponse>> getAll() {
        List<FrequenciaResponse> frequencias = frequenciaService.getAll();
        return ResponseEntity.ok(frequencias);
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('DIRETOR', 'PROFESSOR', 'RESPONSAVEL')")
    public ResponseEntity<FrequenciaResponse> getById(@PathVariable UUID id) {
        FrequenciaResponse frequencia = frequenciaService.getById(id);
        return ResponseEntity.ok(frequencia);
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('DIRETOR', 'PROFESSOR')")
    public ResponseEntity<FrequenciaResponse> update(@PathVariable UUID id, @RequestBody UpdateFrequenciaRequest request) {
        FrequenciaResponse frequencia = frequenciaService.update(id, request);
        return ResponseEntity.ok(frequencia);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('DIRETOR')")
    public ResponseEntity<ApiResponse> delete(@PathVariable UUID id) {
        frequenciaService.delete(id);
        ApiResponse resposta = new ApiResponse("Registro de Frequência " + id + " deletado com sucesso.");
        return ResponseEntity.ok(resposta);
    }
}