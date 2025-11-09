package com.projeto.familiaeduca.application.services;

import com.projeto.familiaeduca.application.exceptions.ResourceNotFoundException;
import com.projeto.familiaeduca.application.mapper.NotaMapper;
import com.projeto.familiaeduca.application.requests.CreateNotaRequest;
import com.projeto.familiaeduca.application.requests.UpdateNotaRequest;
import com.projeto.familiaeduca.application.responses.NotaResponse;
import com.projeto.familiaeduca.domain.models.Aluno;
import com.projeto.familiaeduca.domain.models.Nota;
import com.projeto.familiaeduca.domain.models.Turma;
import com.projeto.familiaeduca.domain.models.Professor;
import com.projeto.familiaeduca.infrastructure.repository.AlunoRepository;
import com.projeto.familiaeduca.infrastructure.repository.NotaRepository;
import com.projeto.familiaeduca.infrastructure.repository.ProfessorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ServiceNota {

    private final NotaRepository notaRepository;
    private final AlunoRepository alunoRepository;
    private final ProfessorRepository professorRepository;
    private final NotaMapper notaMapper;

    public ServiceNota(
            NotaRepository notaRepository,
            AlunoRepository alunoRepository,
            ProfessorRepository professorRepository,
            NotaMapper notaMapper) {
        this.notaRepository = notaRepository;
        this.alunoRepository = alunoRepository;
        this.professorRepository = professorRepository;
        this.notaMapper = notaMapper;
    }

    public NotaResponse create(CreateNotaRequest request) {

        Aluno aluno = alunoRepository.findById(request.getMatriculaAluno())
                .orElseThrow(() -> new ResourceNotFoundException("Aluno com matrícula " + request.getMatriculaAluno() + " não encontrado."));

        Turma turma = aluno.getTurma();
        if (turma == null) {
            throw new ResourceNotFoundException("Aluno não está associado a nenhuma Turma.");
        }


        Professor professor = professorRepository.findById(request.getIdProfessor())
                .orElseThrow(() -> new ResourceNotFoundException("Professor com ID " + request.getIdProfessor() + " não encontrado."));


        Nota nota = new Nota();
        nota.setDisciplina(request.getDisciplina());
        nota.setNota(request.getNota());
        nota.setDataAvaliacao(request.getDataAvaliacao());
        nota.setAluno(aluno);
        nota.setTurma(turma);


        Nota novaNota = notaRepository.save(nota);
        return notaMapper.mappingResponse(novaNota);
    }


    public List<NotaResponse> getAll() {
        return notaRepository.findAll().stream()
                .map(notaMapper::mappingResponse)
                .collect(Collectors.toList());
    }


    public NotaResponse getById(UUID id) {
        Nota nota = notaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registro de Nota com ID " + id + " não encontrado."));

        return notaMapper.mappingResponse(nota);
    }


    public NotaResponse update(UUID id, UpdateNotaRequest request) {
        Nota nota = notaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registro de Nota com ID " + id + " não encontrado."));


        if (request.getDisciplina() != null) {
            nota.setDisciplina(request.getDisciplina());
        }
        if (request.getNota() != null) {
            nota.setNota(request.getNota());
        }
        if (request.getDataAvaliacao() != null) {
            nota.setDataAvaliacao(request.getDataAvaliacao());
        }


        Nota notaAtualizada = notaRepository.save(nota);
        return notaMapper.mappingResponse(notaAtualizada);
    }


    public void delete(UUID id) {
        if (!notaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Registro de Nota com ID " + id + " não encontrado.");
        }
        notaRepository.deleteById(id);
    }
}