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

import java.time.Year;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BoletimService {

    /* Dependências para chamar Repositorys e Mapper */
    private final BoletimRepository boletimRepository;
    private final AlunoRepository alunoRepository;
    private final DiretorRepository diretorRepository;
    private final BoletimMapper boletimMapper;

    public BoletimService(
        BoletimRepository boletimRepository,
        AlunoRepository alunoRepository,
        DiretorRepository diretorRepository,
        BoletimMapper boletimMapper
    ) {
        this.boletimRepository = boletimRepository;
        this.alunoRepository = alunoRepository;
        this.diretorRepository = diretorRepository;
        this.boletimMapper = boletimMapper;
    }

    /* Função que possui a lógica para criação de um boletim */
    public BoletimResponse create(CreateBoletimRequest request) {
        /* Vê se o aluno existe no banco de dados */
        Aluno aluno = alunoRepository.findById(request.getMatriculaAluno())
                .orElseThrow(() -> new ResourceNotFoundException("Aluno com matrícula " + request.getMatriculaAluno() + " não encontrado.")); /* Lança exceção se não existir */

        /* Vê se o diretor existe no banco de dados */
        Diretor diretor = diretorRepository.findById(request.getIdDiretor())
                .orElseThrow(() -> new ResourceNotFoundException("Diretor com id " + request.getIdDiretor() + " não encontrado.")); /* Lança exceção se não existir */

        /* Cria o boletim e chama a função que salva no banco de dados */
        Boletim boletim = new Boletim();
        boletim.setBimestre(request.getBimestre());
        boletim.setAno(Year.of(request.getAno()));
        boletim.setObservacoes(request.getObservacoes());
        boletim.setAluno(aluno);
        boletim.setDiretor(diretor);

        Boletim novoBoletim = boletimRepository.save(boletim);
        return boletimMapper.mappingResponse(novoBoletim);
    }

    /* Função que possui a lógica para retornar a lista com todos os boletins cadastrados */
    public List<BoletimResponse> getAll() {
        return boletimRepository.findAll().stream()
                .map(boletimMapper::mappingResponse)
                .collect(Collectors.toList());
    }

    /* Função que possui a lógica para encontrar um boletim cadastrado */
    public BoletimResponse getById(UUID id) {
        /* Vê se o boletim existe no banco de dados */
        Boletim boletim = boletimRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Boletim com id " + id + " não encontrado.")); /* Lança exceção se não existir */

        return boletimMapper.mappingResponse(boletim);
    }

    /* Função que possui a lógica para retornar a lista de boletins de um aluno */
    public List<BoletimResponse> getByAluno(int matricula) {
        return boletimRepository.findByAlunoMatricula(matricula)
                .stream()
                .map(boletimMapper::mappingResponse)
                .collect(Collectors.toList());
    }

    /* Função que possui a lógica para atualizar as informações de um boletim */
    public BoletimResponse update(UUID id, UpdateBoletimRequest request) {
        /* Vê se o boletim existe no banco de dados */
        Boletim boletim = boletimRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Boletim com id " + id + " não encontrado.")); /* Lança exceção se não existir */

        /* Confere se tudo está preenchido corretamente */
        if (request.getBimestre() != null) {
            boletim.setBimestre(request.getBimestre());
        }

        if (request.getAno() != null) {
            boletim.setAno(Year.of(request.getAno()));
        }

        if (request.getObservacoes() != null) {
            boletim.setObservacoes(request.getObservacoes());
        }

        /* Chama a função que salva o boletim atualizado no banco de dados */
        Boletim boletimAtualizado = boletimRepository.save(boletim);
        return boletimMapper.mappingResponse(boletimAtualizado);
    }

    /* Função que possui a lógica para exclusão de um boletim */
    public void delete(UUID id) {
        /* Vê se o boletim existe no banco de dados */
        if (!boletimRepository.existsById(id)) {
            throw new ResourceNotFoundException("Boletim com id " + id + " não encontrado."); /* Lança exceção se não existir */
        }

        /* Chama a função para excluir o boletim do banco de dados */
        boletimRepository.deleteById(id);
    }
}
