package com.projeto.familiaeduca.application.services;

import com.projeto.familiaeduca.application.exceptions.ResourceNotFoundException;
import com.projeto.familiaeduca.application.mapper.FrequenciaMapper;
import com.projeto.familiaeduca.application.requests.CreateFrequenciaRequest;
import com.projeto.familiaeduca.application.requests.UpdateFrequenciaRequest;
import com.projeto.familiaeduca.application.responses.FrequenciaResponse;
import com.projeto.familiaeduca.domain.models.Aluno;
import com.projeto.familiaeduca.domain.models.Frequencia;
import com.projeto.familiaeduca.domain.models.Professor;
import com.projeto.familiaeduca.domain.models.Turma;
import com.projeto.familiaeduca.infrastructure.repository.AlunoRepository;
import com.projeto.familiaeduca.infrastructure.repository.FrequenciaRepository;
import com.projeto.familiaeduca.infrastructure.repository.ProfessorRepository;
import com.projeto.familiaeduca.infrastructure.repository.TurmaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FrequenciaService {

    private final FrequenciaRepository frequenciaRepository;
    private final AlunoRepository alunoRepository;
    private final TurmaRepository turmaRepository;
    private final ProfessorRepository professorRepository;
    private final FrequenciaMapper frequenciaMapper;

    public FrequenciaService(
            FrequenciaRepository frequenciaRepository,
            AlunoRepository alunoRepository,
            TurmaRepository turmaRepository,
            ProfessorRepository professorRepository,
            FrequenciaMapper frequenciaMapper) {
        this.frequenciaRepository = frequenciaRepository;
        this.alunoRepository = alunoRepository;
        this.turmaRepository = turmaRepository;
        this.professorRepository = professorRepository;
        this.frequenciaMapper = frequenciaMapper;
    }

    public FrequenciaResponse create(CreateFrequenciaRequest request) {
        Aluno aluno = alunoRepository.findById(request.getMatriculaAluno())
                .orElseThrow(() -> new ResourceNotFoundException("Aluno com matrícula " + request.getMatriculaAluno() + " não encontrado."));

        Turma turma = turmaRepository.findById(request.getIdTurma())
                .orElseThrow(() -> new ResourceNotFoundException("Turma com ID " + request.getIdTurma() + " não encontrada."));

        Professor professor = professorRepository.findById(request.getIdProfessor())
                .orElseThrow(() -> new ResourceNotFoundException("Professor com ID " + request.getIdProfessor() + " não encontrado."));

        Frequencia frequencia = new Frequencia();
        frequencia.setData(request.getData());
        frequencia.setPresente(request.getPresente());
        frequencia.setAluno(aluno);
        frequencia.setTurma(turma);

        Frequencia novaFrequencia = frequenciaRepository.save(frequencia);
        return frequenciaMapper.mappingResponse(novaFrequencia);
    }

    public List<FrequenciaResponse> getAll() {
        return frequenciaRepository.findAll().stream()
                .map(frequenciaMapper::mappingResponse)
                .collect(Collectors.toList());
    }

    public FrequenciaResponse getById(UUID id) {
        Frequencia frequencia = frequenciaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registro de Frequência com ID " + id + " não encontrado."));

        return frequenciaMapper.mappingResponse(frequencia);
    }

    public FrequenciaResponse update(UUID id, UpdateFrequenciaRequest request) {
        Frequencia frequencia = frequenciaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registro de Frequência com ID " + id + " não encontrado."));

        if (request.getPresente() != null) {
            frequencia.setPresente(request.getPresente());
        }

        if (request.getData() != null) {
            frequencia.setData(request.getData());
        }

        Frequencia frequenciaAtualizada = frequenciaRepository.save(frequencia);
        return frequenciaMapper.mappingResponse(frequenciaAtualizada);
    }

    public void delete(UUID id) {
        if (!frequenciaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Registro de Frequência com ID " + id + " não encontrado.");
        }
        frequenciaRepository.deleteById(id);
    }
}