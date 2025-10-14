package com.projeto.familiaeduca.api.controllers;

import com.projeto.familiaeduca.application.requests.CreateDiretorRequest;
import com.projeto.familiaeduca.application.requests.UpdateDiretorRequest;
import com.projeto.familiaeduca.application.responses.UsuarioResponse;
import com.projeto.familiaeduca.application.services.DiretorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/diretores")
public class DiretorController {

    private final DiretorService diretorService;

    public DiretorController(DiretorService diretorService) {
        this.diretorService = diretorService;
    }

    @PostMapping
    public ResponseEntity<UsuarioResponse> create(@RequestBody CreateDiretorRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(diretorService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> update(@PathVariable UUID id, @RequestBody UpdateDiretorRequest request) {
        return ResponseEntity.ok(diretorService.update(id, request));
    }
}
