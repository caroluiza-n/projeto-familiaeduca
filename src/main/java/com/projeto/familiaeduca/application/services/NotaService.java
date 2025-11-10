package com.projeto.familiaeduca.application.services;

import com.projeto.familiaeduca.application.exceptions.ResourceNotFoundException;
import com.projeto.familiaeduca.application.mapper.NotaMapper;
import com.projeto.familiaeduca.application.requests.CreateNotaRequest;
import com.projeto.familiaeduca.application.requests.UpdateNotaRequest;
import com.projeto.familiaeduca.application.responses.NotaResponse;
import com.projeto.familiaeduca.domain.models.Boletim;
import com.projeto.familiaeduca.domain.models.Disciplina;
import com.projeto.familiaeduca.domain.models.Nota;
import com.projeto.familiaeduca.infrastructure.repository.*;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class NotaService {

    /* Dependências para chamar Repositorys e Mapper */
    private final NotaRepository notaRepository;
    private final BoletimRepository boletimRepository;
    private final DisciplinaRepository disciplinaRepository;
    private final NotaMapper notaMapper;

    public NotaService(
        NotaRepository notaRepository,
        BoletimRepository boletimRepository,
        DisciplinaRepository disciplinaRepository,
        NotaMapper notaMapper
    ) {
        this.notaRepository = notaRepository;
        this.boletimRepository = boletimRepository;
        this.disciplinaRepository = disciplinaRepository;
        this.notaMapper = notaMapper;
    }

    /* Função que possui a lógica para criação de uma nota */
    public NotaResponse create(CreateNotaRequest request) {
        /* Vê se o boletim existe no banco de dados */
        Boletim boletim = boletimRepository.findById(request.getIdBoletim())
                .orElseThrow(() -> new ResourceNotFoundException("Boletim com id " + request.getIdBoletim() + " não encontrado.")); /* Lança exceção se não existir */

        /* Vê se a disciplina existe no banco de dados */
        Disciplina disciplina = disciplinaRepository.findById(request.getIdDisciplina())
                .orElseThrow(() -> new ResourceNotFoundException("Disciplina com id " + request.getIdDisciplina() + " não encontrada.")); /* Lança exceção se não existir */

        /* Cria a nota */
        Nota nota = new Nota();
        nota.setNota(request.getNota());
        nota.setDataAvaliacao(request.getDataAvaliacao());
        nota.setBoletim(boletim);
        nota.setDisciplina(disciplina);
        nota.setAluno(boletim.getAluno());

        /* Busca o aluno e a turma com base no boletim */
        if (boletim.getAluno().getTurma() != null) {
            nota.setTurma(boletim.getAluno().getTurma());
        }

        /* Chama a função que salva no banco de dados */
        Nota novaNota = notaRepository.save(nota);
        return notaMapper.mappingResponse(novaNota);
    }

    /* Função que possui a lógica para retornar a lista com todas as notas cadastradas */
    public List<NotaResponse> getAll() {
        return notaRepository.findAll().stream()
                .map(notaMapper::mappingResponse)
                .collect(Collectors.toList());
    }

    /* Função que possui a lógica para encontrar uma nota cadastrada */
    public NotaResponse getById(UUID id) {
        /* Vê se o registro de nota existe no banco de dados */
        Nota nota = notaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registro de nota com id " + id + " não encontrado.")); /* Lança exceção se não existir */

        return notaMapper.mappingResponse(nota);
    }

    /* Função que possui a lógica para retornar a lista de notas de um aluno */
    public List<NotaResponse> getByAluno(int matricula) {
        return notaRepository.findByAlunoMatricula(matricula).stream()
                .map(notaMapper::mappingResponse)
                .collect(Collectors.toList());
    }

    /* Função que possui a lógica para retornar a lista de notas de uma disciplina */
    public List<NotaResponse> getByDisciplina(UUID idDisciplina) {
        return notaRepository.findByDisciplinaId(idDisciplina)
                .stream()
                .map(notaMapper::mappingResponse)
                .collect(Collectors.toList());
    }

    /* Função que possui a lógica para retornar a lista de notas de um aluno em uma determinada disciplina */
    public List<NotaResponse> getByAlunoAndDisciplina(int matricula, UUID idDisciplina) {
        return notaRepository.findByAlunoMatriculaAndDisciplinaId(matricula, idDisciplina)
                .stream()
                .map(notaMapper::mappingResponse)
                .collect(Collectors.toList());
    }

    /* Função que possui a lógica para atualizar as informações de uma nota */
    public NotaResponse update(UUID id, UpdateNotaRequest request) {
        /* Vê se o registro de nota existe no banco de dados */
        Nota nota = notaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registro de nota com id " + id + " não encontrado.")); /* Lança exceção se não existir */

        /* Confere se tudo está preenchido corretamente */
        if (request.getNota() != null) {
            nota.setNota(request.getNota());
        }
        if (request.getDataAvaliacao() != null) {
            nota.setDataAvaliacao(request.getDataAvaliacao());
        }

        /* Chama a função que salva a nota atualizada no banco de dados */
        Nota notaAtualizada = notaRepository.save(nota);
        return notaMapper.mappingResponse(notaAtualizada);
    }

    /* Função que possui a lógica para exclusão de uma nota */
    public void delete(UUID id) {
        /* Vê se o registro de nota existe no banco de dados */
        if (!notaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Registro de nota com id " + id + " não encontrado."); /* Lança exceção se não existir */
        }

        /* Chama a função para excluir o registro de nota do banco de dados */
        notaRepository.deleteById(id);
    }
}
