package com.projeto.familiaeduca.application.services;

import com.projeto.familiaeduca.application.exceptions.ResourceNotFoundException;
import com.projeto.familiaeduca.application.mapper.ChecklistProfessorMapper;
import com.projeto.familiaeduca.application.requests.CreateChecklistProfessorRequest;
import com.projeto.familiaeduca.application.requests.UpdateChecklistProfessorRequest;
import com.projeto.familiaeduca.application.responses.ChecklistProfessorResponse;
import com.projeto.familiaeduca.domain.models.Aluno;
import com.projeto.familiaeduca.domain.models.ChecklistProfessor;
import com.projeto.familiaeduca.domain.models.Professor;
import com.projeto.familiaeduca.infrastructure.repository.AlunoRepository;
import com.projeto.familiaeduca.infrastructure.repository.ChecklistProfessorRepository;
import com.projeto.familiaeduca.infrastructure.repository.ProfessorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ServiceChecklistProfessor{

    private final ChecklistProfessorRepository checklistProfessorRepository;
    private final AlunoRepository alunoRepository;
    private final ProfessorRepository professorRepository;
    private final ChecklistProfessorMapper checklistProfessorMapper;

    public ServiceChecklistProfessor(
            ChecklistProfessorRepository checklistProfessorRepository,
            AlunoRepository alunoRepository,
            ProfessorRepository professorRepository,
            ChecklistProfessorMapper checklistProfessorMapper) {
        this.checklistProfessorRepository = checklistProfessorRepository;
        this.alunoRepository = alunoRepository;
        this.professorRepository = professorRepository;
        this.checklistProfessorMapper = checklistProfessorMapper;
    }

    public ChecklistProfessorResponse create(CreateChecklistProfessorRequest request) {
        Aluno aluno = alunoRepository.findById(request.getMatriculaAluno())
                .orElseThrow(() -> new ResourceNotFoundException("Aluno com matrícula " + request.getMatriculaAluno() + " não encontrado."));

        Professor professor = professorRepository.findById(request.getIdProfessor())
                .orElseThrow(() -> new ResourceNotFoundException("Professor com ID " + request.getIdProfessor() + " não encontrado."));

        ChecklistProfessor checklist = new ChecklistProfessor();
        checklist.setDataChecklist(request.getDataChecklist());
        checklist.setObservacoes(request.getObservacoes());
        checklist.setAluno(aluno);
        checklist.setProfessor(professor);

        ChecklistProfessor novoChecklist = checklistProfessorRepository.save(checklist);
        return checklistProfessorMapper.mappingResponse(novoChecklist);
    }

    public List<ChecklistProfessorResponse> getAll() {
        return checklistProfessorRepository.findAll().stream()
                .map(checklistProfessorMapper::mappingResponse)
                .collect(Collectors.toList());
    }

    public ChecklistProfessorResponse getById(UUID id) {
        ChecklistProfessor checklist = checklistProfessorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Checklist de Professor com ID " + id + " n encontrado."));

        return checklistProfessorMapper.mappingResponse(checklist);
    }

    public ChecklistProfessorResponse update(UUID id, UpdateChecklistProfessorRequest request) {
        ChecklistProfessor checklist = checklistProfessorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Checklist de Professor com ID " + id + " n encontrado."));

        if (request.getObservacoes() != null) {
            checklist.setObservacoes(request.getObservacoes());
        }

        ChecklistProfessor checklistAtualizado = checklistProfessorRepository.save(checklist);
        return checklistProfessorMapper.mappingResponse(checklistAtualizado);
    }

    public void delete(UUID id) {
        if (!checklistProfessorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Checklist de Professor com ID " + id + " não encontrado.");
        }
        checklistProfessorRepository.deleteById(id);
    }
}