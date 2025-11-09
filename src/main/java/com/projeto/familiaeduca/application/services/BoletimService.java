package com.projeto.familiaeduca.application.services;

import com.projeto.familiaeduca.application.exceptions.ResourceNotFoundException;
import com.projeto.familiaeduca.application.mapper.BoletimMapper;
import com.projeto.familiaeduca.application.requests.CreateBoletimRequest;
import com.projeto.familiaeduca.application.requests.UpdateBoletimRequest;
import com.projeto.familiaeduca.application.responses.BoletimResponse;
import com.projeto.familiaeduca.domain.models.Aluno;
import com.projeto.familiaeduca.domain.models.Boletim;
import com.projeto.familiaeduca.domain.models.Diretor;
import com.projeto.familiaeduca.infrastructure.repository.AlunoRepository;
import com.projeto.familiaeduca.infrastructure.repository.BoletimRepository;
import com.projeto.familiaeduca.infrastructure.repository.DiretorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BoletimService {

    private final BoletimRepository boletimRepository;
    private final AlunoRepository alunoRepository;
    private final DiretorRepository diretorRepository;
    private final BoletimMapper boletimMapper;

    public BoletimService(
            BoletimRepository boletimRepository,
            AlunoRepository alunoRepository,
            DiretorRepository diretorRepository,
            BoletimMapper boletimMapper) {
        this.boletimRepository = boletimRepository;
        this.alunoRepository = alunoRepository;
        this.diretorRepository = diretorRepository;
        this.boletimMapper = boletimMapper;
    }

    public BoletimResponse create(CreateBoletimRequest request) {
        Aluno aluno = alunoRepository.findById(request.getMatriculaAluno())
                .orElseThrow(() -> new ResourceNotFoundException("Aluno com matrícula " + request.getMatriculaAluno() + " não encontrado."));

        Diretor diretor = diretorRepository.findById(request.getIdDiretor())
                .orElseThrow(() -> new ResourceNotFoundException("Diretor com ID " + request.getIdDiretor() + " não encontrado."));

        Boletim boletim = new Boletim();
        boletim.setBimestre(request.getBimestre());
        boletim.setAno(request.getAno());
        boletim.setObservacoes(request.getObservacoes());
        boletim.setAluno(aluno);
        boletim.setDiretor(diretor);

        Boletim novoBoletim = boletimRepository.save(boletim);
        return boletimMapper.mappingResponse(novoBoletim);
    }

    public List<BoletimResponse> getAll() {
        return boletimRepository.findAll().stream()
                .map(boletimMapper::mappingResponse)
                .collect(Collectors.toList());
    }

    public BoletimResponse getById(UUID id) {
        Boletim boletim = boletimRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Boletim com ID " + id + " não encontrado."));

        return boletimMapper.mappingResponse(boletim);
    }

    public BoletimResponse update(UUID id, UpdateBoletimRequest request) {
        Boletim boletim = boletimRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Boletim com ID " + id + " não encontrado."));

        if (request.getBimestre() != null) {
            boletim.setBimestre(request.getBimestre());
        }
        if (request.getAno() != null) {
            boletim.setAno(request.getAno());
        }
        if (request.getObservacoes() != null) {
            boletim.setObservacoes(request.getObservacoes());
        }

        Boletim boletimAtualizado = boletimRepository.save(boletim);
        return boletimMapper.mappingResponse(boletimAtualizado);
    }

    public void delete(UUID id) {
        if (!boletimRepository.existsById(id)) {
            throw new ResourceNotFoundException("Boletim com ID " + id + " não encontrado.");
        }
        boletimRepository.deleteById(id);
    }
}