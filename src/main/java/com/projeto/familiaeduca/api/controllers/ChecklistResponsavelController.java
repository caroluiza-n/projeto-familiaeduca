package com.projeto.familiaeduca.api.controllers;

import com.projeto.familiaeduca.application.exceptions.response.ApiResponse;
import com.projeto.familiaeduca.application.requests.CreateChecklistResponsavelRequest;
import com.projeto.familiaeduca.application.requests.UpdateChecklistResponsavelRequest;
import com.projeto.familiaeduca.application.responses.ChecklistResponsavelResponse;
import com.projeto.familiaeduca.application.services.ChecklistResponsavelService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/checklists/responsaveis")
public class ChecklistResponsavelController {

    /* Dependência para chamar funções de Service */
    private final ChecklistResponsavelService checklistResponsavelService;

    public ChecklistResponsavelController(ChecklistResponsavelService checklistResponsavelService) {
        this.checklistResponsavelService = checklistResponsavelService;
    }

    /* Endpoint para inserir um checklist */
    @PostMapping
    @PreAuthorize("hasAnyRole('DIRETOR', 'RESPONSAVEL')") /* Quem pode fazer é o diretor ou o professor */
    public ResponseEntity<ChecklistResponsavelResponse> create(@Valid @RequestBody CreateChecklistResponsavelRequest request) {
        ChecklistResponsavelResponse checklist = checklistResponsavelService.create(request); /* Chama a função que cria */
        return ResponseEntity.status(HttpStatus.CREATED).body(checklist); /* Cria o checklist e retorna as informações dele */
    }

    @GetMapping
    @PreAuthorize("hasRole('DIRETOR')") /* Quem pode fazer é o diretor */
    public ResponseEntity<List<ChecklistResponsavelResponse>> getAll() {
        List<ChecklistResponsavelResponse> checklists = checklistResponsavelService.getAll(); /* Chama a função que faz o GET */
        return ResponseEntity.ok(checklists); /* Retorna a lista de checklists */
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('DIRETOR', 'RESPONSAVEL')") /* Quem pode fazer é o diretor ou o professor */
    public ResponseEntity<ChecklistResponsavelResponse> getById(@PathVariable UUID id) {
        ChecklistResponsavelResponse checklist = checklistResponsavelService.getById(id); /* Chama a função que faz o GET */
        return ResponseEntity.ok(checklist); /* Retorna a checklist */
    }

    /* Endpoint para a atualizar informações de um checklist */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('DIRETOR', 'RESPONSAVEL')") /* Quem pode fazer é o diretor ou o professor */
    public ResponseEntity<ChecklistResponsavelResponse> update(@PathVariable UUID id, @RequestBody UpdateChecklistResponsavelRequest request) {
        ChecklistResponsavelResponse checklist = checklistResponsavelService.update(id, request); /* Chama a função que faz a atualização */
        return ResponseEntity.ok(checklist); /* Retorna o checklist com as informações atualizadas */
    }

    /* Endpoint para a exclusão de um checklist */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('DIRETOR')") /* Quem pode fazer é o diretor */
    public ResponseEntity<ApiResponse> delete(@PathVariable UUID id) {
        checklistResponsavelService.delete(id); /* Chama a função que faz a exclusão */
        ApiResponse resposta = new ApiResponse("Checklist de Responsável com id " + id + " deletado com sucesso.");
        return ResponseEntity.ok(resposta); /* Retorna a resposta acima */
    }
}
