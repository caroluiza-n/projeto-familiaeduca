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
public class ControllerChecklistResponsavel {

    private final ChecklistResponsavelService checklistResponsavelService;

    public ChecklistResponsavelController(ChecklistResponsavelService checklistResponsavelService) {
        this.checklistResponsavelService = checklistResponsavelService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('DIRETOR', 'RESPONSAVEL')")
    public ResponseEntity<ChecklistResponsavelResponse> create(@Valid @RequestBody CreateChecklistResponsavelRequest request) {
        ChecklistResponsavelResponse checklist = checklistResponsavelService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(checklist);
    }

    @GetMapping
    @PreAuthorize("hasRole('DIRETOR')")
    public ResponseEntity<List<ChecklistResponsavelResponse>> getAll() {
        List<ChecklistResponsavelResponse> checklists = checklistResponsavelService.getAll();
        return ResponseEntity.ok(checklists);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('DIRETOR', 'RESPONSAVEL')")
    public ResponseEntity<ChecklistResponsavelResponse> getById(@PathVariable UUID id) {
        ChecklistResponsavelResponse checklist = checklistResponsavelService.getById(id);
        return ResponseEntity.ok(checklist);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('DIRETOR', 'RESPONSAVEL')")
    public ResponseEntity<ChecklistResponsavelResponse> update(@PathVariable UUID id, @RequestBody UpdateChecklistResponsavelRequest request) {
        ChecklistResponsavelResponse checklist = checklistResponsavelService.update(id, request);
        return ResponseEntity.ok(checklist);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('DIRETOR')")
    public ResponseEntity<ApiResponse> delete(@PathVariable UUID id) {
        checklistResponsavelService.delete(id);
        ApiResponse resposta = new ApiResponse("Checklist de Responsável com ID " + id + " deletado com sucesso.");
        return ResponseEntity.ok(resposta);
    }
}