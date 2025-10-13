package com.projeto.familiaeduca.api.controllers;

import com.projeto.familiaeduca.application.exceptions.response.ApiResponse;
import com.projeto.familiaeduca.application.requests.*;
import com.projeto.familiaeduca.application.responses.UsuarioResponse;
import com.projeto.familiaeduca.application.services.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> getAll() {
        List<UsuarioResponse> usuarios = usuarioService.getAll();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> getById(@PathVariable UUID id) {
        UsuarioResponse usuario = usuarioService.getById(id);
        return ResponseEntity.ok(usuario);
    }

    @PutMapping("senha/{id}")
    public ResponseEntity<ApiResponse> updatePassword(@PathVariable UUID id, @RequestBody UpdatePasswordRequest request) {
        usuarioService.updatePassword(id, request);
        return ResponseEntity.ok(new ApiResponse("Senha atualizada com sucesso."));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/diretor")
    public ResponseEntity<UsuarioResponse> createDiretor(@RequestBody CreateDiretorRequest request) {
        UsuarioResponse usuario = usuarioService.createDiretor(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }

    @PutMapping("/diretor/{id}")
    public ResponseEntity<UsuarioResponse> updateDiretor(@PathVariable UUID id, @RequestBody UpdateDiretorRequest request) {
        UsuarioResponse usuario = usuarioService.updateDiretor(id, request);
        return ResponseEntity.ok(usuario);
    }

    @PostMapping("/professor")
    public ResponseEntity<UsuarioResponse> createProfessor(@RequestBody CreateProfessorRequest request) {
        UsuarioResponse usuario = usuarioService.createProfessor(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }

    @PutMapping("/professor/{id}")
    public ResponseEntity<UsuarioResponse> updateProfessor(@PathVariable UUID id, @RequestBody UpdateProfessorRequest request) {
        UsuarioResponse usuario = usuarioService.updateProfessor(id, request);
        return ResponseEntity.ok(usuario);
    }

    @PostMapping("/responsavel")
    public ResponseEntity<UsuarioResponse> createResponsavel(@RequestBody CreateResponsavelRequest request) {
        UsuarioResponse usuario = usuarioService.createResponsavel(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }

    @PutMapping("/responsavel/{id}")
    public ResponseEntity<UsuarioResponse> updateResponsavel(@PathVariable UUID id, @RequestBody UpdateResponsavelRequest request) {
        UsuarioResponse usuario = usuarioService.updateResponsavel(id, request);
        return ResponseEntity.ok(usuario);
    }
}
