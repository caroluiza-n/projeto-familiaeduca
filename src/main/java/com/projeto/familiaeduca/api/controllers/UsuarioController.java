package com.projeto.familiaeduca.api.controllers;

import com.projeto.familiaeduca.application.exceptions.response.ApiResponse;
import com.projeto.familiaeduca.application.requests.*;
import com.projeto.familiaeduca.application.responses.UsuarioResponse;
import com.projeto.familiaeduca.application.services.UsuarioService;
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

    @PostMapping("/login")
    public ResponseEntity<UsuarioResponse> login(@RequestBody LoginRequest request) {
        UsuarioResponse usuario = usuarioService.login(request);
        return ResponseEntity.ok(usuario);
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
}
