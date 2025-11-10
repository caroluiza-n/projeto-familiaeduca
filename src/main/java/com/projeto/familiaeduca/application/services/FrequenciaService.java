package com.projeto.familiaeduca.application.services;

import com.projeto.familiaeduca.application.exceptions.BusinessRuleException;
import com.projeto.familiaeduca.application.exceptions.DataIntegrityException;
import com.projeto.familiaeduca.application.exceptions.ResourceNotFoundException;
import com.projeto.familiaeduca.application.mapper.FrequenciaMapper;
import com.projeto.familiaeduca.application.requests.CreateFrequenciaRequest;
import com.projeto.familiaeduca.application.requests.UpdateFrequenciaRequest;
import com.projeto.familiaeduca.application.responses.FrequenciaResponse;
import com.projeto.familiaeduca.domain.models.Aluno;
import com.projeto.familiaeduca.domain.models.Frequencia;
import com.projeto.familiaeduca.domain.models.Turma;
import com.projeto.familiaeduca.infrastructure.repository.AlunoRepository;
import com.projeto.familiaeduca.infrastructure.repository.FrequenciaRepository;
import com.projeto.familiaeduca.infrastructure.repository.TurmaRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FrequenciaService {

    /* Dependências para chamar Repositorys e Mapper */
    private final FrequenciaRepository frequenciaRepository;
    private final AlunoRepository alunoRepository;
    private final TurmaRepository turmaRepository;
    private final FrequenciaMapper frequenciaMapper;

    public FrequenciaService(
        FrequenciaRepository frequenciaRepository,
        AlunoRepository alunoRepository,
        TurmaRepository turmaRepository,
        FrequenciaMapper frequenciaMapper
    ) {
        this.frequenciaRepository = frequenciaRepository;
        this.alunoRepository = alunoRepository;
        this.turmaRepository = turmaRepository;
        this.frequenciaMapper = frequenciaMapper;
    }

    /* Função que possui a lógica para criação de um registro de frequência */
    public FrequenciaResponse create(CreateFrequenciaRequest request) {
        /* Vê se o registro já existe no banco dde dados */
        if (frequenciaRepository.findByAlunoMatriculaAndData(request.getMatriculaAluno(), request.getData()).isPresent()) {
            throw new DataIntegrityException("Já existe um registro de frequência para este aluno nesta data."); /* Lança exceção se existir */
        }

        /* Vê se o aluno já existe no banco dde dados */
        Aluno aluno = alunoRepository.findById(request.getMatriculaAluno())
                .orElseThrow(() -> new ResourceNotFoundException("Aluno com matrícula " + request.getMatriculaAluno() + " não encontrado.")); /* Lança exceção se não existir */

        /* Vê se a turma já existe no banco dde dados */
        Turma turma = turmaRepository.findById(request.getIdTurma())
                .orElseThrow(() -> new ResourceNotFoundException("Turma com id " + request.getIdTurma() + " não encontrada.")); /* Lança exceção se não existir */

        /* Vê se o aluno pertence a turma */
        if (aluno.getTurma() == null || !aluno.getTurma().getId().equals(turma.getId())) {
            throw new BusinessRuleException("O aluno informado não pertence à turma informada."); /* Lança exceção se não pertencer */
        }

        /* Cria o registro e chama a função que salva no banco de dados */
        Frequencia frequencia = new Frequencia();
        frequencia.setData(request.getData());
        frequencia.setPresente(request.getPresente());
        frequencia.setAluno(aluno);
        frequencia.setTurma(turma);

        Frequencia novaFrequencia = frequenciaRepository.save(frequencia);
        return frequenciaMapper.mappingResponse(novaFrequencia);
    }

    /* Função que possui a lógica para retornar a lista com todas as frequências cadastradas */
    public List<FrequenciaResponse> getAll() {
        return frequenciaRepository.findAll().stream()
                .map(frequenciaMapper::mappingResponse)
                .collect(Collectors.toList());
    }

    /* Função que possui a lógica para encontrar uma frequência cadastrada */
    public FrequenciaResponse getById(UUID id) {
        /* Vê se o registro existe no banco de dados */
        Frequencia frequencia = frequenciaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registro de Frequência com id " + id + " não encontrado.")); /* Lança exceção se não existir */

        return frequenciaMapper.mappingResponse(frequencia);
    }

    /* Função que possui a lógica para retornar a lista de frequências de um aluno */
    public List<FrequenciaResponse> getByAluno(int matricula) {
        return frequenciaRepository.findByAlunoMatricula(matricula).stream()
                .map(frequenciaMapper::mappingResponse)
                .collect(Collectors.toList());
    }

    /* Função que possui a lógica para retornar a lista de frequências da turma em uma determinada data */
    public List<FrequenciaResponse> getByTurmaAndData(UUID idTurma, LocalDate data) {
        return frequenciaRepository.findByTurmaIdAndData(idTurma, data).stream()
                .map(frequenciaMapper::mappingResponse)
                .collect(Collectors.toList());
    }

    /* Função que possui a lógica para atualizar as informações de uma frequência */
    public FrequenciaResponse update(UUID id, UpdateFrequenciaRequest request) {
        /* Vê se o registro existe no banco de dados */
        Frequencia frequencia = frequenciaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registro de Frequência com id " + id + " não encontrado.")); /* Lança exceção se não existir */

        /* Confere se tudo está preenchido corretamente */
        if (request.getPresente() != null) {
            frequencia.setPresente(request.getPresente());
        }

        if (request.getData() != null) {
            frequencia.setData(request.getData());
        }

        /* Chama a função que salva a frequência atualizada no banco de dados */
        Frequencia frequenciaAtualizada = frequenciaRepository.save(frequencia);
        return frequenciaMapper.mappingResponse(frequenciaAtualizada);
    }

    /* Função que possui a lógica para exclusão de uma frequência */
    public void delete(UUID id) {
        /* Vê se o registro existe no banco de dados */
        if (!frequenciaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Registro de Frequência com id " + id + " não encontrado.");
        }

        /* Chama a função para excluir o registro do banco de dados */
        frequenciaRepository.deleteById(id);
    }
}
