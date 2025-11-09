package com.projeto.familiaeduca.application.services;

import com.projeto.familiaeduca.application.exceptions.ResourceNotFoundException;
import com.projeto.familiaeduca.application.mapper.ChecklistResponsavelMapper;
import com.projeto.familiaeduca.application.requests.CreateChecklistResponsavelRequest;
import com.projeto.familiaeduca.application.requests.UpdateChecklistResponsavelRequest;
import com.projeto.familiaeduca.application.responses.ChecklistResponsavelResponse;
import com.projeto.familiaeduca.domain.models.Aluno;
import com.projeto.familiaeduca.domain.models.ChecklistResponsavel;
import com.projeto.familiaeduca.domain.models.Responsavel;
import com.projeto.familiaeduca.infrastructure.repository.AlunoRepository;
import com.projeto.familiaeduca.infrastructure.repository.ChecklistResponsavelRepository;
import com.projeto.familiaeduca.infrastructure.repository.ResponsavelRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ServiceChecklistResponsavel {

    private final ChecklistResponsavelRepository checklistResponsavelRepository;
    private final AlunoRepository alunoRepository;
    private final ResponsavelRepository responsavelRepository;
    private final ChecklistResponsavelMapper checklistResponsavelMapper;

    public ServiceChecklistResponsavel(
            ChecklistResponsavelRepository checklistResponsavelRepository,
            AlunoRepository alunoRepository,
            ResponsavelRepository responsavelRepository,
            ChecklistResponsavelMapper checklistResponsavelMapper) {
        this.checklistResponsavelRepository = checklistResponsavelRepository;
        this.alunoRepository = alunoRepository;
        this.responsavelRepository = responsavelRepository;
        this.checklistResponsavelMapper = checklistResponsavelMapper;
    }

    public ChecklistResponsavelResponse create(CreateChecklistResponsavelRequest request) {
        Aluno aluno = alunoRepository.findById(request.getMatriculaAluno())
                .orElseThrow(() -> new ResourceNotFoundException("Aluno com matrícula " + request.getMatriculaAluno() + " não encontrado."));

        Responsavel responsavel = responsavelRepository.findById(request.getIdResponsavel())
                .orElseThrow(() -> new ResourceNotFoundException("Responsável com ID " + request.getIdResponsavel() + " não encontrado."));

        ChecklistResponsavel checklist = new ChecklistResponsavel();
        checklist.setDataChecklist(request.getDataChecklist());
        checklist.setItensVerificados(request.getItensVerificados());
        checklist.setAluno(aluno);
        checklist.setResponsavel(responsavel);

        ChecklistResponsavel novoChecklist = checklistResponsavelRepository.save(checklist);
        return checklistResponsavelMapper.mappingResponse(novoChecklist);
    }

    public List<ChecklistResponsavelResponse> getAll() {
        return checklistResponsavelRepository.findAll().stream()
                .map(checklistResponsavelMapper::mappingResponse)
                .collect(Collectors.toList());
    }

    public ChecklistResponsavelResponse getById(UUID id) {
        ChecklistResponsavel checklist = checklistResponsavelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Checklist de Responsável com ID " + id + " não encontrado."));

        return checklistResponsavelMapper.mappingResponse(checklist);
    }

    public ChecklistResponsavelResponse update(UUID id, UpdateChecklistResponsavelRequest request) {
        ChecklistResponsavel checklist = checklistResponsavelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Checklist de Responsável com ID " + id + " não encontrado."));

        if (request.getItensVerificados() != null) {
            checklist.setItensVerificados(request.getItensVerificados());
        }

        ChecklistResponsavel checklistAtualizado = checklistResponsavelRepository.save(checklist);
        return checklistResponsavelMapper.mappingResponse(checklistAtualizado);
    }

    public void delete(UUID id) {
        if (!checklistResponsavelRepository.existsById(id)) {
            throw new ResourceNotFoundException("Checklist de Responsável com ID " + id + " não encontrado.");
        }
        checklistResponsavelRepository.deleteById(id);
    }
}