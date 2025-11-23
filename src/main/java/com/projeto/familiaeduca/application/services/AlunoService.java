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

    /* Dependências para chamar Repositorys e Mapper */
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

    /* Função que possui a lógica para criação de um aluno */
    public AlunoResponse create(CreateAlunoRequest request) {
        /* Vê se a turma existe no banco de dados */
        Turma turma = turmaRepository.findById(request.getIdTurma())
                .orElseThrow(() -> new ResourceNotFoundException("Turma com id " +  request.getIdTurma() + "não encontrada.")); /* Lança exceção se não existir */

        /* Vê se o responsável existe no banco de dados */
        Responsavel responsavel = responsavelRepository.findById(request.getIdResponsavel())
                .orElseThrow(() -> new ResourceNotFoundException("Responsável com id " + request.getIdResponsavel() + " não encontrado.")); /* Lança exceção se não existir */

        Integer ultimaMatricula = alunoRepository.encontrarUltimaMatricula();
        int proximaMatricula = ultimaMatricula + 1;

        /* Vê se a matrícula já está cadastrada no banco de dados
        if(alunoRepository.existsById(request.getMatricula())) {
            throw new DataIntegrityException("A matrícula " + request.getMatricula() + "já está em uso."); // Lança exceção se estiver cadastrada
        }   */

        /* Cria o aluno e chama a função que salva no banco de dados */
        Aluno aluno = new Aluno();
        aluno.setMatricula(proximaMatricula);
        aluno.setNome(request.getNome());
        aluno.setDataNascimento(request.getDataNascimento());
        aluno.setLaudo(request.getLaudo());
        aluno.setAlergias(request.getAlergias());
        aluno.setTurma(turma);
        aluno.setResponsavel(responsavel);

        Aluno novoAluno = alunoRepository.save(aluno);
        return alunoMapper.mappingResponse(novoAluno);
    }

    /* Função que possui a lógica para retornar a lista com todos os alunos cadastrados */
    public List<AlunoResponse> getAll() {
        return alunoRepository.findAll().stream()
                .map(alunoMapper::mappingResponse)
                .collect(Collectors.toList());
    }

    /* Função que possui a lógica para encontrar um aluno cadastrado */
    public AlunoResponse getById(int matricula) {
        /* Vê se o aluno existe no banco de dados */
        Aluno aluno = alunoRepository.findById(matricula)
                .orElseThrow(() -> new ResourceNotFoundException("Aluno com matrícula " + matricula + " não encontrado.")); /* Lança exceção se não existir */

        return alunoMapper.mappingResponse(aluno);
    }

    /* Função que possui a lógica para atualizar as informações de um aluno */
    public AlunoResponse update(int matricula, UpdateAlunoRequest request) {
        /* Vê se o aluno existe no banco de dados */
        Aluno aluno = alunoRepository.findById(matricula)
                .orElseThrow(() -> new ResourceNotFoundException("Aluno com matrícula " + matricula + " não encontrado.")); /* Lança exceção se não existir */

        /* Confere se tudo está preenchido corretamente */
        if(request.getNome() != null) aluno.setNome(request.getNome());
        if(request.getDataNascimento() != null) aluno.setDataNascimento(request.getDataNascimento());
        if(request.getLaudo() != null) aluno.setLaudo(request.getLaudo());
        if(request.getAlergias() != null) aluno.setAlergias(request.getAlergias());

        if(request.getIdTurma() != null) {
            /* Vê se a turma existe no banco de dados */
            Turma novaTurma = turmaRepository.findById(request.getIdTurma())
                    .orElseThrow(() -> new ResourceNotFoundException("Turma com id " + request.getIdTurma() + " não encontrada.")); /* Lança exceção se não existir */
            aluno.setTurma(novaTurma);
        }

        if(request.getIdResponsavel() != null) {
            /* Vê se o responsável existe no banco de dados */
            Responsavel novoResponsavel = responsavelRepository.findById(request.getIdResponsavel())
                    .orElseThrow(() -> new ResourceNotFoundException("Responsável com ID " + request.getIdResponsavel() + " não encontrado.")); /* Lança exceção se não existir */
            aluno.setResponsavel(novoResponsavel);
        }

        /* Chama a função que salva o aluno atualizado no banco de dados */
        Aluno alunoAtualizado = alunoRepository.save(aluno);

        return alunoMapper.mappingResponse(alunoAtualizado);
    }

    /* Função que possui a lógica para exclusão de um aluno */
    public void delete(int matricula) {
        /* Vê se o aluno existe no banco de dados */
        if(!alunoRepository.existsById(matricula)) {
            throw new ResourceNotFoundException("Aluno com matrícula " + matricula + " não encontrado."); /* Lança exceção se não existir */
        }

        /* Chama a função para excluir o aluno do banco de dados */
        alunoRepository.deleteById(matricula);
    }
}
