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
public class ControllerChecklistProfessor {

    private final ChecklistProfessorService checklistProfessorService;

    public ChecklistProfessorController(ChecklistProfessorService checklistProfessorService) {
        this.checklistProfessorService = checklistProfessorService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('DIRETOR', 'PROFESSOR')")
    public ResponseEntity<ChecklistProfessorResponse> create(@Valid @RequestBody CreateChecklistProfessorRequest request) {
        ChecklistProfessorResponse checklist = checklistProfessorService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(checklist);
    }

    @GetMapping
    @PreAuthorize("hasRole('DIRETOR')")
    public ResponseEntity<List<ChecklistProfessorResponse>> getAll() {
        List<ChecklistProfessorResponse> checklists = checklistProfessorService.getAll();
        return ResponseEntity.ok(checklists);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('DIRETOR', 'PROFESSOR')")
    public ResponseEntity<ChecklistProfessorResponse> getById(@PathVariable UUID id) {
        ChecklistProfessorResponse checklist = checklistProfessorService.getById(id);
        return ResponseEntity.ok(checklist);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('DIRETOR', 'PROFESSOR')")
    public ResponseEntity<ChecklistProfessorResponse> update(@PathVariable UUID id, @RequestBody UpdateChecklistProfessorRequest request) {
        ChecklistProfessorResponse checklist = checklistProfessorService.update(id, request);
        return ResponseEntity.ok(checklist);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('DIRETOR')")
    public ResponseEntity<ApiResponse> delete(@PathVariable UUID id) {
        checklistProfessorService.delete(id);
        ApiResponse resposta = new ApiResponse("Checklist de Professor com ID " + id + " deletado com sucesso.");
        return ResponseEntity.ok(resposta);
    }
}