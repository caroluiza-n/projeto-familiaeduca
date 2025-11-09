package com.projeto.familiaeduca.api.controllers;

import com.projeto.familiaeduca.application.exceptions.response.ApiResponse;
import com.projeto.familiaeduca.application.requests.CreateReuniaoRequest;
import com.projeto.familiaeduca.application.requests.UpdateReuniaoRequest;
import com.projeto.familiaeduca.application.responses.ReuniaoResponse;
import com.projeto.familiaeduca.application.services.ReuniaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

    @RestController
    @RequestMapping("/reunioes")
    public class ReuniaoController {

        private final ReuniaoService reuniaoService;

        public ReuniaoController(ReuniaoService reuniaoService) {
            this.reuniaoService = reuniaoService;
        }

        @PostMapping
        @PreAuthorize("hasAnyRole('DIRETOR', 'PROFESSOR')")
        public ResponseEntity<ReuniaoResponse> create(@Valid @RequestBody CreateReuniaoRequest request) {
            ReuniaoResponse reuniao = reuniaoService.create(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(reuniao);
        }

        @GetMapping
        @PreAuthorize("hasRole('DIRETOR')")
        public ResponseEntity<List<ReuniaoResponse>> getAll() {
            List<ReuniaoResponse> reunioes = reuniaoService.getAll();
            return ResponseEntity.ok(reunioes);
        }

        @GetMapping("/{id}")
        @PreAuthorize("hasAnyRole('DIRETOR', 'PROFESSOR', 'RESPONSAVEL')")
        public ResponseEntity<ReuniaoResponse> getById(@PathVariable UUID id) {
            ReuniaoResponse reuniao = reuniaoService.getById(id);
            return ResponseEntity.ok(reuniao);
        }

        @PutMapping("/{id}")
        @PreAuthorize("hasAnyRole('DIRETOR', 'PROFESSOR')")
        public ResponseEntity<ReuniaoResponse> update(@PathVariable UUID id, @RequestBody UpdateReuniaoRequest request) {
            ReuniaoResponse reuniao = reuniaoService.update(id, request);
            return ResponseEntity.ok(reuniao);
        }

        @DeleteMapping("/{id}")
        @PreAuthorize("hasRole('DIRETOR')")
        public ResponseEntity<ApiResponse> delete(@PathVariable UUID id) {
            reuniaoService.delete(id);
            ApiResponse resposta = new ApiResponse("Reunião com ID " + id + " cancelada com sucesso.");
            return ResponseEntity.ok(resposta);
        }
    }

