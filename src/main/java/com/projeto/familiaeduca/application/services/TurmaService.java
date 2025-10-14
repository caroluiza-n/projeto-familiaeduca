package com.projeto.familiaeduca.application.services;

import com.projeto.familiaeduca.application.exceptions.BusinessRuleException;
import com.projeto.familiaeduca.application.exceptions.DataIntegrityException;
import com.projeto.familiaeduca.application.exceptions.ResourceNotFoundException;
import com.projeto.familiaeduca.application.mapper.TurmaMapper;
import com.projeto.familiaeduca.application.requests.TurmaRequest;
import com.projeto.familiaeduca.application.responses.TurmaResponse;
import com.projeto.familiaeduca.domain.models.Professor;
import com.projeto.familiaeduca.domain.models.Turma;
import com.projeto.familiaeduca.infrastructure.repository.ProfessorRepository;
import com.projeto.familiaeduca.infrastructure.repository.TurmaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TurmaService {

    private final TurmaRepository turmaRepository;
    private final ProfessorRepository professorRepository;
    private final TurmaMapper turmaMapper;

    public TurmaService(TurmaRepository turmaRepository,  ProfessorRepository professorRepository, TurmaMapper turmaMapper) {
        this.turmaRepository = turmaRepository;
        this.professorRepository = professorRepository;
        this.turmaMapper = turmaMapper;
    }

    public TurmaResponse create(TurmaRequest request) {
        if(turmaRepository.existsByNome(request.getNome())) {
            throw new DataIntegrityException("Já existe uma turma com o nome " + request.getNome() + ".");
        }

        Professor professor = professorRepository.findById(request.getIdProfessor())
                .orElseThrow(() -> new ResourceNotFoundException("Professor com id " + request.getIdProfessor() + " não encontrado."));

        if(turmaRepository.existsByIdProfessor(request.getIdProfessor())) {
            throw new DataIntegrityException("O professor " + professor.getNome() + "já está em outra turma.");
        }

        Turma turma = new Turma();
        turma.setNome(request.getNome());
        turma.setProfessor(professor);

        return turmaMapper.mappingResponse(turmaRepository.save(turma));
    }

    public List<TurmaResponse> getAll() {
        return turmaRepository.findAll().stream()
                .map(turmaMapper::mappingResponse)
                .collect(Collectors.toList());
    }

    public TurmaResponse getById(UUID id) {
        Turma turma = turmaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Turma com id " + id + " não encontrada."));
        return turmaMapper.mappingResponse(turma);
    }

    public TurmaResponse update(UUID id, TurmaRequest request) {
        Turma turma = turmaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Turma com id " + id + " não encontrada."));

        if (request.getNome() != null) {
            turma.setNome(request.getNome());
        }
        if (request.getIdProfessor() != null) {
            Professor novoProfessor = professorRepository.findById(request.getIdProfessor())
                    .orElseThrow(() -> new ResourceNotFoundException("Professor com id " + request.getIdProfessor() + " não encontrado."));

            turma.setProfessor(novoProfessor);
        }

        return turmaMapper.mappingResponse(turmaRepository.save(turma));
    }

    public void delete(UUID id) {
        Turma turma = turmaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Turma com id " + id + " não encontrada."));

        if (!turma.getAlunos().isEmpty()) {
            throw new BusinessRuleException("Não é possível deletar a turma, alunos estão matriculados.");
        }

        turmaRepository.delete(turma);
    }
}
