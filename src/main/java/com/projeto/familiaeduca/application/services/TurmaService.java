package com.projeto.familiaeduca.application.services;

import com.projeto.familiaeduca.application.exceptions.BusinessRuleException;
import com.projeto.familiaeduca.application.exceptions.DataIntegrityException;
import com.projeto.familiaeduca.application.exceptions.ResourceNotFoundException;
import com.projeto.familiaeduca.application.mapper.TurmaMapper;
import com.projeto.familiaeduca.application.requests.TurmaRequest;
import com.projeto.familiaeduca.application.responses.TurmaResponse;
import com.projeto.familiaeduca.application.responses.TurmaResumeResponse;
import com.projeto.familiaeduca.domain.models.Disciplina;
import com.projeto.familiaeduca.domain.models.Professor;
import com.projeto.familiaeduca.domain.models.Turma;
import com.projeto.familiaeduca.infrastructure.repository.DisciplinaRepository;
import com.projeto.familiaeduca.infrastructure.repository.ProfessorRepository;
import com.projeto.familiaeduca.infrastructure.repository.TurmaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TurmaService {
    /* Dependências para chamar Repositorys e Mapper */
    private final TurmaRepository turmaRepository;
    private final ProfessorRepository professorRepository;
    private final DisciplinaRepository disciplinaRepository;
    private final TurmaMapper turmaMapper;

    public TurmaService(TurmaRepository turmaRepository,  ProfessorRepository professorRepository, DisciplinaRepository disciplinaRepository, TurmaMapper turmaMapper) {
        this.turmaRepository = turmaRepository;
        this.professorRepository = professorRepository;
        this.disciplinaRepository = disciplinaRepository;
        this.turmaMapper = turmaMapper;
    }

    /* Função que possui a lógica para criação de uma turma */
    public TurmaResponse create(TurmaRequest request) {
        if(turmaRepository.existsByNome(request.getNome())) {
            throw new DataIntegrityException("Já existe uma turma com o nome " + request.getNome() + ".");
        }

        Professor professor = professorRepository.findById(request.getIdProfessor())
                .orElseThrow(() -> new ResourceNotFoundException("Professor com id " + request.getIdProfessor() + " não encontrado."));

        if(turmaRepository.existsByProfessorId(request.getIdProfessor())) {
            throw new DataIntegrityException("O professor " + professor.getNome() + "já está em outra turma.");
        }
        /* Cria a nota */
        Turma turma = new Turma();
        turma.setNome(request.getNome());
        turma.setProfessor(professor);
        List<Disciplina> disciplinasPadrao = disciplinaRepository.findByPadraoTrue();
        turma.setDisciplinas(new HashSet<>(disciplinasPadrao));

        return turmaMapper.mappingResponse(turmaRepository.save(turma));
    }
    /* Função que possui a lógica para retornar a lista com todas as turmas cadastradas para diretor e apenas a turma que pertence ao professor */
    public List<TurmaResumeResponse> getAll() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        String roles = auth.getAuthorities().toString();

        List<Turma> turmas;

        if (roles.contains("PROFESSOR") && !roles.contains("DIRETOR")) {
            turmas = turmaRepository.findByProfessorEmail(email);
        } else {
            turmas = turmaRepository.findAll();
        }

        return turmas.stream()
                .map(turmaMapper::mappingResumeResponse)
                .collect(Collectors.toList());
    }

    /* Função que possui a lógica para encontrar uma turma cadastrada */
    public TurmaResponse getById(UUID id) {
        Turma turma = turmaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Turma com id " + id + " não encontrada."));
        return turmaMapper.mappingResponse(turma);
    }

    /* Função que possui a lógica para atualizar as informações de uma turma */
    public TurmaResponse update(UUID id, TurmaRequest request) {
        Turma turma = turmaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Turma com id " + id + " não encontrada."));

        if(request.getNome() != null) {
            turma.setNome(request.getNome());
        }
        if(request.getIdProfessor() != null) {
            Professor novoProfessor = professorRepository.findById(request.getIdProfessor())
                    .orElseThrow(() -> new ResourceNotFoundException("Professor com id " + request.getIdProfessor() + " não encontrado."));

            turma.setProfessor(novoProfessor);
        }

        return turmaMapper.mappingResponse(turmaRepository.save(turma));
    }

    /* Função que possui a lógica para exclusão de uma turma */
    public void delete(UUID id) {
        Turma turma = turmaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Turma com id " + id + " não encontrada."));

        if(!turma.getAlunos().isEmpty()) {
            throw new BusinessRuleException("Não é possível deletar a turma, alunos estão matriculados.");
        }

        /* Chama a função para excluir o registro de turma do banco de dados */
        turmaRepository.delete(turma);
    }
}
