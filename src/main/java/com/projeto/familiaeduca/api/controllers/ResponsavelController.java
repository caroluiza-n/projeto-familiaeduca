package com.projeto.familiaeduca.api.controllers;

import com.projeto.familiaeduca.application.requests.CreateResponsavelRequest;
import com.projeto.familiaeduca.application.requests.UpdateResponsavelRequest;
import com.projeto.familiaeduca.application.responses.UsuarioResponse;
import com.projeto.familiaeduca.application.services.ResponsavelService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/responsaveis")
public class ResponsavelController {

    private final ResponsavelService responsavelService;

    public ResponsavelController(ResponsavelService responsavelService) {
        this.responsavelService = responsavelService;
    }

    @PostMapping
    public ResponseEntity<UsuarioResponse> create(@Valid @RequestBody CreateResponsavelRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(responsavelService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> update(@PathVariable UUID id, @RequestBody UpdateResponsavelRequest request) {
        return ResponseEntity.ok(responsavelService.update(id, request));
    }
}
