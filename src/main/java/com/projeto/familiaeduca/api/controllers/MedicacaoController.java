package com.projeto.familiaeduca.api.controllers;

import com.projeto.familiaeduca.application.exceptions.response.ApiResponse;
import com.projeto.familiaeduca.application.requests.CreateMedicacaoRequest;
import com.projeto.familiaeduca.application.responses.MedicacaoResponse;
import com.projeto.familiaeduca.application.services.MedicacaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/medicacoes")
public class MedicacaoController {
    private final MedicacaoService medicacaoService;

    public MedicacaoController(MedicacaoService medicacaoService) {
        this.medicacaoService = medicacaoService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('DIRETOR', 'PROFESSOR')")
    public ResponseEntity<MedicacaoResponse> create(@Valid @RequestBody CreateMedicacaoRequest request,
                                                    @AuthenticationPrincipal UserDetails userDetails)
    {
        MedicacaoResponse response = medicacaoService.create(request, userDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/aluno/{matricula}")
    @PreAuthorize("hasAnyRole('DIRETOR', 'PROFESSOR', 'RESPONSAVEL')")
    public ResponseEntity<List<MedicacaoResponse>> findByAluno(@PathVariable int matricula,
                                                               @AuthenticationPrincipal UserDetails userDetails) {
        List<MedicacaoResponse> historico = medicacaoService.findByAluno(matricula, userDetails);
        return ResponseEntity.ok(historico);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('DIRETOR')")
    public ResponseEntity<ApiResponse> delete(@PathVariable UUID id) {
        medicacaoService.delete(id);
        return ResponseEntity.ok(new ApiResponse("Registro de medicação com id " + id + " deletado com sucesso."));
    }
}
