package com.projeto.familiaeduca.application.services;

import com.projeto.familiaeduca.application.exceptions.DataIntegrityException;
import com.projeto.familiaeduca.application.exceptions.ResourceNotFoundException;
import com.projeto.familiaeduca.application.mapper.AlunoMapper;
import com.projeto.familiaeduca.application.requests.CreateAlunoRequest;
import com.projeto.familiaeduca.application.requests.UpdateAlunoRequest;
import com.projeto.familiaeduca.application.responses.AlunoResponse;
import com.projeto.familiaeduca.domain.models.Aluno;
import com.projeto.familiaeduca.domain.models.Responsavel;
import com.projeto.familiaeduca.domain.models.Turma;
import com.projeto.familiaeduca.infrastructure.repository.AlunoRepository;
import com.projeto.familiaeduca.infrastructure.repository.ResponsavelRepository;
import com.projeto.familiaeduca.infrastructure.repository.TurmaRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;
    private final TurmaRepository turmaRepository;
    private final ResponsavelRepository responsavelRepository;
    private final AlunoMapper alunoMapper;

    public AlunoService(
            AlunoRepository alunoRepository,
            TurmaRepository turmaRepository,
            ResponsavelRepository responsavelRepository,
            AlunoMapper alunoMapper
    ) {
        this.alunoRepository = alunoRepository;
        this.turmaRepository = turmaRepository;
        this.responsavelRepository = responsavelRepository;
        this.alunoMapper = alunoMapper;
    }

    public AlunoResponse create(CreateAlunoRequest request) {
        if(alunoRepository.existsById(request.getMatricula())) {
            throw new DataIntegrityException("A matrícula " + request.getMatricula() + "já está em uso.");
        }

        Turma turma = turmaRepository.findById(request.getIdTurma())
                .orElseThrow(() -> new ResourceNotFoundException("Turma com id " +  request.getIdTurma() + "não encontrada."));

        Responsavel responsavel = responsavelRepository.findById(request.getIdResponsavel())
                .orElseThrow(() -> new ResourceNotFoundException("Responsável com id " + request.getIdResponsavel() + " não encontrado."));

        Aluno aluno = new Aluno();
        aluno.setMatricula(request.getMatricula());
        aluno.setNome(request.getNome());
        aluno.setDataNascimento(request.getDataNascimento());
        aluno.setLaudo(request.getLaudo());
        aluno.setAlergias(request.getAlergias());
        aluno.setTurma(turma);
        aluno.setResponsavel(responsavel);

        Aluno novoAluno = alunoRepository.save(aluno);
        return alunoMapper.mappingResponse(novoAluno);
    }

    public List<AlunoResponse> getAll() {
        return alunoRepository.findAll().stream()
                .map(alunoMapper::mappingResponse)
                .collect(Collectors.toList());
    }

    public AlunoResponse getById(int matricula) {
        Aluno aluno = alunoRepository.findById(matricula)
                .orElseThrow(() -> new ResourceNotFoundException("Aluno com matrícula " + matricula + " não encontrado."));
        return alunoMapper.mappingResponse(aluno);
    }

    public AlunoResponse update(int matricula, UpdateAlunoRequest request) {
        Aluno aluno = alunoRepository.findById(matricula)
                .orElseThrow(() -> new ResourceNotFoundException("Aluno com matrícula " + matricula + " não encontrado."));

        if(request.getNome() != null) aluno.setNome(request.getNome());
        if(request.getDataNascimento() != null) aluno.setDataNascimento(request.getDataNascimento());
        if(request.getLaudo() != null) aluno.setLaudo(request.getLaudo());
        if(request.getAlergias() != null) aluno.setAlergias(request.getAlergias());

        if(request.getIdTurma() != null) {
            Turma novaTurma = turmaRepository.findById(request.getIdTurma())
                    .orElseThrow(() -> new ResourceNotFoundException("Turma com id " + request.getIdTurma() + " não encontrada."));
            aluno.setTurma(novaTurma);
        }

        if(request.getIdResponsavel() != null) {
            Responsavel novoResponsavel = responsavelRepository.findById(request.getIdResponsavel())
                    .orElseThrow(() -> new ResourceNotFoundException("Responsável com ID " + request.getIdResponsavel() + " não encontrado."));
            aluno.setResponsavel(novoResponsavel);
        }

        Aluno alunoAtualizado = alunoRepository.save(aluno);
        return alunoMapper.mappingResponse(alunoAtualizado);
    }

    public void delete(int matricula) {
        if(!alunoRepository.existsById(matricula)) {
            throw new ResourceNotFoundException("Aluno com matrícula " + matricula + " não encontrado.");
        }
        alunoRepository.deleteById(matricula);
    }
}
