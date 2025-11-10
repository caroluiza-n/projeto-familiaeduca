package com.projeto.familiaeduca.api.controllers;

import com.projeto.familiaeduca.application.exceptions.response.ApiResponse;
import com.projeto.familiaeduca.application.requests.CreateFrequenciaRequest;
import com.projeto.familiaeduca.application.requests.UpdateFrequenciaRequest;
import com.projeto.familiaeduca.application.responses.FrequenciaResponse;
import com.projeto.familiaeduca.application.services.FrequenciaService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/frequencias")
public class FrequenciaController {

    /* Dependência para chamar funções de Service */
    private final FrequenciaService frequenciaService;

    public FrequenciaController(FrequenciaService frequenciaService) {
        this.frequenciaService = frequenciaService;
    }

    /* Endpoint para inserir um registro de frequência */
    @PostMapping
    @PreAuthorize("hasAnyRole('DIRETOR', 'PROFESSOR')") /* Quem pode fazer é o diretor ou o professor */
    public ResponseEntity<FrequenciaResponse> create(@Valid @RequestBody CreateFrequenciaRequest request) {
        FrequenciaResponse frequencia = frequenciaService.create(request); /* Chama a função que cria */
        return ResponseEntity.status(HttpStatus.CREATED).body(frequencia); /* Cria o registro e retorna as informações dele */
    }

    /* Endpoint para buscar a lista de todas as frequência cadastradas */
    @GetMapping
    @PreAuthorize("hasRole('DIRETOR')") /* Quem pode fazer é o diretor */
    public ResponseEntity<List<FrequenciaResponse>> getAll() {
        List<FrequenciaResponse> frequencias = frequenciaService.getAll(); /* Chama a função que faz o GET */
        return ResponseEntity.ok(frequencias); /* Retorna a lista de frequências */
    }

    /* Endpoint para buscar a frequência pelo id */
    @GetMapping("/{id}")
    public ResponseEntity<FrequenciaResponse> getById(@PathVariable UUID id) {
        FrequenciaResponse frequencia = frequenciaService.getById(id); /* Chama a função que faz o GET */
        return ResponseEntity.ok(frequencia); /* Retorna a frequência */
    }

    /* Endppoint para buscar o histórico de frequências de um aluno */
    @GetMapping("/aluno/{matricula}")
    public ResponseEntity<List<FrequenciaResponse>> getByAluno(@PathVariable int matricula) {
        List<FrequenciaResponse> frequencias = frequenciaService.getByAluno(matricula); /* Chama a função que faz o GET */
        return ResponseEntity.ok(frequencias); /* Retorna a lista de frequências */
    }

    /* Endppoint para buscar o histórico de frequências de uma turma em uma data */
    @GetMapping("/turma/{idTurma}")
    @PreAuthorize("hasAnyRole('DIRETOR', 'PROFESSOR')") /* Quem pode fazer é o diretor ou o professor */
    public ResponseEntity<List<FrequenciaResponse>> getByTurmaAndData(
            @PathVariable UUID idTurma,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {

        /* Se não inserir a data, fica a data atual */
        LocalDate dataConsulta = (data != null) ? data : LocalDate.now();

        List<FrequenciaResponse> frequencias = frequenciaService.getByTurmaAndData(idTurma, dataConsulta); /* Chama a função que faz o GET */
        return ResponseEntity.ok(frequencias); /* Retorna a lista de frequências */
    }

    /* Endpoint para a atualizar informações de uma frequência */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('DIRETOR', 'PROFESSOR')") /* Quem pode fazer é o diretor ou o professor */
    public ResponseEntity<FrequenciaResponse> update(@PathVariable UUID id, @RequestBody UpdateFrequenciaRequest request) {
        FrequenciaResponse frequencia = frequenciaService.update(id, request); /* Chama a função que faz a atualização */
        return ResponseEntity.ok(frequencia); /* Retorna a frequência com as informações atualizadas */
    }

    /* Endpoint para a exclusão de uma frequência */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('DIRETOR')") /* Quem pode fazer é o diretor */
    public ResponseEntity<ApiResponse> delete(@PathVariable UUID id) {
        frequenciaService.delete(id); /* Chama a função que faz a exclusão */
        ApiResponse resposta = new ApiResponse("Registro de Frequência " + id + " deletado com sucesso.");
        return ResponseEntity.ok(resposta); /* Retorna a resposta acima */
    }
}
