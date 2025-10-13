package com.projeto.familiaeduca.api.controllers;

import com.projeto.familiaeduca.application.exceptions.response.ApiResponse;
import com.projeto.familiaeduca.application.requests.CreateAlunoRequest;
import com.projeto.familiaeduca.application.requests.UpdateAlunoRequest;
import com.projeto.familiaeduca.application.responses.AlunoResponse;
import com.projeto.familiaeduca.application.services.AlunoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    private final AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @PostMapping
    public ResponseEntity<AlunoResponse> create(@RequestBody CreateAlunoRequest request) {
        AlunoResponse aluno = alunoService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(aluno);
    }

    @GetMapping
    public ResponseEntity<List<AlunoResponse>> getAll() {
        List<AlunoResponse> alunos = alunoService.getAll();
        return ResponseEntity.ok(alunos);
    }

    @GetMapping("/{matricula}")
    public ResponseEntity<AlunoResponse> getById(@PathVariable int matricula) {
        AlunoResponse aluno = alunoService.getById(matricula);
        return ResponseEntity.ok(aluno);
    }

    @PutMapping("/{matricula}")
    public ResponseEntity<AlunoResponse> update(@PathVariable int matricula, @RequestBody UpdateAlunoRequest request) {
        AlunoResponse aluno = alunoService.update(matricula, request);
        return ResponseEntity.ok(aluno);
    }

    @DeleteMapping("/{matricula}")
    public ResponseEntity<ApiResponse> delete(@PathVariable int matricula) {
        alunoService.delete(matricula);
        ApiResponse resposta = new ApiResponse("Aluno com matrícula " + matricula + " deletado com sucesso.");
        return ResponseEntity.ok(resposta);
    }
}
