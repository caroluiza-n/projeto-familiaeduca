package com.projeto.familiaeduca.api.controllers;

import com.projeto.familiaeduca.application.exceptions.response.ApiResponse;
import com.projeto.familiaeduca.application.requests.CreateChecklistProfessorRequest;
import com.projeto.familiaeduca.application.requests.UpdateChecklistProfessorRequest;
import com.projeto.familiaeduca.application.responses.ChecklistProfessorResponse;
import com.projeto.familiaeduca.application.services.ChecklistProfessorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/checklists/professores")
public class ChecklistProfessorController {

    /* Dependência para chamar funções de Service */
    private final ChecklistProfessorService checklistProfessorService;

    public ChecklistProfessorController(ChecklistProfessorService checklistProfessorService) {
        this.checklistProfessorService = checklistProfessorService;
    }

    /* Endpoint para inserir um checklist */
    @PostMapping
    @PreAuthorize("hasAnyRole('DIRETOR', 'PROFESSOR')") /* Quem pode fazer é o diretor ou o professor */
    public ResponseEntity<ChecklistProfessorResponse> create(@Valid @RequestBody CreateChecklistProfessorRequest request) {
        ChecklistProfessorResponse checklist = checklistProfessorService.create(request); /* Chama a função que cria */
        return ResponseEntity.status(HttpStatus.CREATED).body(checklist); /* Cria o checklist e retorna as informações dele */
    }

    /* Endpoint para buscar a lista de todas os checklists cadastradas */
    @GetMapping
    @PreAuthorize("hasRole('DIRETOR')") /* Quem pode fazer é o diretor */
    public ResponseEntity<List<ChecklistProfessorResponse>> getAll() {
        List<ChecklistProfessorResponse> checklists = checklistProfessorService.getAll(); /* Chama a função que faz o GET */
        return ResponseEntity.ok(checklists); /* Retorna a lista de checklists */
    }

    /* Endpoint para buscar o checklist pelo id */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('DIRETOR', 'PROFESSOR')") /* Quem pode fazer é o diretor ou o professor */
    public ResponseEntity<ChecklistProfessorResponse> getById(@PathVariable UUID id) {
        ChecklistProfessorResponse checklist = checklistProfessorService.getById(id); /* Chama a função que faz o GET */
        return ResponseEntity.ok(checklist); /* Retorna a checklist */
    }

    /* Endpoint para a atualizar informações de um checklist */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('DIRETOR', 'PROFESSOR')") /* Quem pode fazer é o diretor ou o professor */
    public ResponseEntity<ChecklistProfessorResponse> update(@PathVariable UUID id, @RequestBody UpdateChecklistProfessorRequest request) {
        ChecklistProfessorResponse checklist = checklistProfessorService.update(id, request); /* Chama a função que faz a atualização */
        return ResponseEntity.ok(checklist); /* Retorna o checklist com as informações atualizadas */
    }

    /* Endpoint para a exclusão de um checklist */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('DIRETOR')") /* Quem pode fazer é o diretor */
    public ResponseEntity<ApiResponse> delete(@PathVariable UUID id) {
        checklistProfessorService.delete(id); /* Chama a função que faz a exclusão */
        ApiResponse resposta = new ApiResponse("Checklist de Professor com id " + id + " deletado com sucesso.");
        return ResponseEntity.ok(resposta); /* Retorna a resposta acima */
    }
}
