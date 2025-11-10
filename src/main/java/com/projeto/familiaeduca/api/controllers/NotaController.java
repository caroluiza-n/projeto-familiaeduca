package com.projeto.familiaeduca.api.controllers;

import com.projeto.familiaeduca.application.exceptions.response.ApiResponse;
import com.projeto.familiaeduca.application.requests.CreateNotaRequest;
import com.projeto.familiaeduca.application.requests.UpdateNotaRequest;
import com.projeto.familiaeduca.application.responses.NotaResponse;
import com.projeto.familiaeduca.application.services.NotaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/notas")
public class NotaController{

    /* Dependência para chamar funções de Service */
    private final NotaService notaService;

    public NotaController(NotaService notaService) {
        this.notaService = notaService;
    }

    /* Endpoint para inserir um registro de nota */
    @PostMapping
    @PreAuthorize("hasAnyRole('DIRETOR', 'PROFESSOR')") /* Quem pode fazer é o diretor ou o professor */
    public ResponseEntity<NotaResponse> create(@Valid @RequestBody CreateNotaRequest request) {
        NotaResponse nota = notaService.create(request); /* Chama a função que cria */
        return ResponseEntity.status(HttpStatus.CREATED).body(nota); /* Cria o registro e retorna as informações dele */
    }

    /* Endpoint para buscar a lista de todas as notas cadastradas */
    @GetMapping
    @PreAuthorize("hasRole('DIRETOR')") /* Quem pode fazer é o diretor */
    public ResponseEntity<List<NotaResponse>> getAll() {
        List<NotaResponse> notas = notaService.getAll(); /* Chama a função que faz o GET */
        return ResponseEntity.ok(notas); /* Retorna a lista de notas */
    }

    /* Endpoint para buscar a nota pelo id */
    @GetMapping("/{id}")
    public ResponseEntity<NotaResponse> getById(@PathVariable UUID id) {
        NotaResponse nota = notaService.getById(id); /* Chama a função que faz o GET */
        return ResponseEntity.ok(nota); /* Retorna a nota */
    }

    /* Endpoint para buscar as notas de um aluno */
    @GetMapping("/aluno/{matricula}")
    public ResponseEntity<List<NotaResponse>> getByAluno(@PathVariable int matricula) {
        List<NotaResponse> nota = notaService.getByAluno(matricula); /* Chama a função que faz o GET */
        return ResponseEntity.ok(nota); /* Retorna a lista de notas do aluno */
    }

    /* Endpoint para buscar as notas de uma determinada disciplina */
    @GetMapping("/disciplina/{idDisciplina}")
    @PreAuthorize("hasAnyRole('DIRETOR', 'PROFESSOR')") /* Quem pode fazer é o diretor ou o professor */
    public ResponseEntity<List<NotaResponse>> getByDisciplina(@PathVariable UUID idDisciplina) {
        List<NotaResponse> notas = notaService.getByDisciplina(idDisciplina); /* Chama a função que faz o GET */
        return ResponseEntity.ok(notas); /* Retorna a lista de notas da disciplina */
    }

    /* Endpoint para buscar as notas de um aluno por disciplina */
    @GetMapping("/aluno/{matricula}/disciplina/{idDisciplina}")
    public ResponseEntity<List<NotaResponse>> getByAlunoAndDisciplina(@PathVariable int matricula, @PathVariable UUID idDisciplina) {
        List<NotaResponse> notas = notaService.getByAlunoAndDisciplina(matricula, idDisciplina); /* Chama a função que faz o GET */
        return ResponseEntity.ok(notas); /* Retorna a lista de notas do aluno na disciplina */
    }

    /* Endpoint para a atualizar informações de uma nota */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('DIRETOR', 'PROFESSOR')") /* Quem pode fazer é o diretor ou o professor */
    public ResponseEntity<NotaResponse> update(@PathVariable UUID id, @RequestBody UpdateNotaRequest request) {
        NotaResponse nota = notaService.update(id, request); /* Chama a função que faz a atualização */
        return ResponseEntity.ok(nota); /* Retorna a nota com as informações atualizadas */
    }

    /* Endpoint para a exclusão de uma nota */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('DIRETOR', 'PROFESSOR')") /* Quem pode fazer é o diretor ou o professor */
    public ResponseEntity<ApiResponse> delete(@PathVariable UUID id) {
        notaService.delete(id); /* Chama a função que faz a exclusão */
        ApiResponse resposta = new ApiResponse("Registro de nota com id " + id + " deletado com sucesso.");
        return ResponseEntity.ok(resposta); /* Retorna a resposta acima */
    }
}
