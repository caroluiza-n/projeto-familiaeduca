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

    /* Dependência para chamar funções de Service */
    private final ReuniaoService reuniaoService;

    public ReuniaoController(ReuniaoService reuniaoService) {
        this.reuniaoService = reuniaoService;
    }

    /* Endpoint para inserir uma reunião */
    @PostMapping
    @PreAuthorize("hasAnyRole('DIRETOR', 'PROFESSOR')") /* Quem pode fazer é o diretor ou o professor */
    public ResponseEntity<ReuniaoResponse> create(@Valid @RequestBody CreateReuniaoRequest request) {
        ReuniaoResponse reuniao = reuniaoService.create(request); /* Chama a função que cria */
        return ResponseEntity.status(HttpStatus.CREATED).body(reuniao); /* Cria a reunião e retorna as informações dele */
    }

    /* Endpoint para buscar a lista de todas as reuniões cadastradas */
    @GetMapping
    @PreAuthorize("hasRole('DIRETOR')") /* Quem pode fazer é o diretor */
    public ResponseEntity<List<ReuniaoResponse>> getAll() {
        List<ReuniaoResponse> reunioes = reuniaoService.getAll(); /* Chama a função que faz o GET */
        return ResponseEntity.ok(reunioes); /* Retorna a lista de reuniões */
    }

    /* Endpoint para buscar a reunião pelo id */
    @GetMapping("/{id}")
    public ResponseEntity<ReuniaoResponse> getById(@PathVariable UUID id) {
        ReuniaoResponse reuniao = reuniaoService.getById(id); /* Chama a função que faz o GET */
        return ResponseEntity.ok(reuniao); /* Retorna a reunião */
    }

    /* Endpoint para buscar as reuniões de um responsável */
    @GetMapping("/responsavel/{idResponsavel}")
    public ResponseEntity<List<ReuniaoResponse>> getByResponsavel(@PathVariable UUID responsavelId) {
        List<ReuniaoResponse> reunioes = reuniaoService.getByResponsavel(responsavelId); /* Chama a função que faz o GET */
        return ResponseEntity.ok(reunioes); /* Retorna a lista de reuniões do responsável */
    }

    /* Endpoint para a atualizar informações de uma reunião */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('DIRETOR', 'PROFESSOR')") /* Quem pode fazer é o diretor ou o professor */
    public ResponseEntity<ReuniaoResponse> update(@PathVariable UUID id, @RequestBody UpdateReuniaoRequest request) {
        ReuniaoResponse reuniao = reuniaoService.update(id, request); /* Chama a função que faz a atualização */
        return ResponseEntity.ok(reuniao); /* Retorna a reunião com as informações atualizadas */
    }

    /* Endpoint para a exclusão de uma reunião */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('DIRETOR')") /* Quem pode fazer é o diretor */
    public ResponseEntity<ApiResponse> delete(@PathVariable UUID id) {
        reuniaoService.delete(id); /* Chama a função que faz a exclusão */
        ApiResponse resposta = new ApiResponse("Reunião com id " + id + " cancelada com sucesso.");
        return ResponseEntity.ok(resposta); /* Retorna a resposta acima */
    }
}
