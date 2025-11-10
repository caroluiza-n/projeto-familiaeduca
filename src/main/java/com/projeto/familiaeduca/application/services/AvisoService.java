package com.projeto.familiaeduca.application.services;

import com.projeto.familiaeduca.application.exceptions.ResourceNotFoundException;
import com.projeto.familiaeduca.application.mapper.AvisoMapper;
import com.projeto.familiaeduca.application.requests.CreateAvisoRequest;
import com.projeto.familiaeduca.application.requests.UpdateAvisoRequest;
import com.projeto.familiaeduca.application.responses.AvisoResponse;
import com.projeto.familiaeduca.domain.models.Aviso;
import com.projeto.familiaeduca.domain.models.Diretor;
import com.projeto.familiaeduca.domain.models.Professor;
import com.projeto.familiaeduca.domain.models.Responsavel;
import com.projeto.familiaeduca.infrastructure.repository.AvisoRepository;
import com.projeto.familiaeduca.infrastructure.repository.DiretorRepository;
import com.projeto.familiaeduca.infrastructure.repository.ProfessorRepository;
import com.projeto.familiaeduca.infrastructure.repository.ResponsavelRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AvisoService {

    /* Dependências para chamar Repositorys e Mapper */
    private final AvisoRepository avisoRepository;
    private final DiretorRepository diretorRepository;
    private final ProfessorRepository professorRepository;
    private final ResponsavelRepository responsavelRepository;
    private final AvisoMapper avisoMapper;

    public AvisoService(
        AvisoRepository avisoRepository,
        DiretorRepository diretorRepository,
        ProfessorRepository professorRepository,
        ResponsavelRepository responsavelRepository,
        AvisoMapper avisoMapper
    ) {
        this.avisoRepository = avisoRepository;
        this.diretorRepository = diretorRepository;
        this.professorRepository = professorRepository;
        this.responsavelRepository = responsavelRepository;
        this.avisoMapper = avisoMapper;
    }

    /* Função que possui a lógica para criação de um aviso */
    public AvisoResponse create(CreateAvisoRequest request) {
        /* Vê se o diretor existe no banco de dados */
        Diretor diretor = diretorRepository.findById(request.getIdDiretor())
                .orElseThrow(() -> new ResourceNotFoundException("Diretor com id " + request.getIdDiretor() + " não encontrado.")); /* Lança exceção se não existir */

        /* Vê se cada professor que receberá o aviso existe no banco de dados */
        Set<Professor> professores = request.getIdProfessores().stream()
                .map(id -> professorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Professor com id " + id + " não encontrado."))) /* Lança exceção se não existir */
                .collect(Collectors.toSet());

        /* Vê se cada responsável que receberá o Aviso existe no banco de dados */
        Set<Responsavel> responsaveis = request.getIdResponsaveis().stream()
                .map(id -> responsavelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Responsável com id " + id + " não encontrado."))) /* Lança exceção se não existir */
                .collect(Collectors.toSet());

        /* Cria o aviso e chama a função que salva no banco de dados */
        Aviso aviso = new Aviso();
        aviso.setTitulo(request.getTitulo());
        aviso.setMensagem(request.getMensagem());
        aviso.setDataPublicacao(LocalDate.now());
        aviso.setDiretor(diretor);
        aviso.setAvisosProfessores(professores);
        aviso.setAvisosResponsaveis(responsaveis);

        Aviso novoAviso = avisoRepository.save(aviso);
        return avisoMapper.mappingResponse(novoAviso);
    }

    /* Função que possui a lógica para retornar a lista com todos os avisos cadastrados */
    public List<AvisoResponse> getAll() {

        return avisoRepository.findAll().stream()
                .map(avisoMapper::mappingResponse)
                .collect(Collectors.toList());
    }

    /* Função que possui a lógica para encontrar um aviso cadastrado */
    public AvisoResponse getById(UUID id) {
        /* Vê se o aviso existe no banco de dados */
        Aviso aviso = avisoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aviso com id " + id + " não encontrado.")); /* Lança exceção se não existir */

        return avisoMapper.mappingResponse(aviso);
    }

    /* Função que possui a lógica para atualizar as informações de um aviso */
    public AvisoResponse update(UUID id, UpdateAvisoRequest request) {
        /* Vê se o aviso existe no banco de dados */
        Aviso aviso = avisoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aviso com id " + id + " não encontrado.")); /* Lança exceção se não existir */

        /* Confere se tudo está preenchido corretamente */
        if (request.getTitulo() != null) {
            aviso.setTitulo(request.getTitulo());
        }

        if (request.getMensagem() != null) {
            aviso.setMensagem(request.getMensagem());
        }

        if (request.getIdProfessores() != null) {
            /* Vê se cada professor que receberá o aviso existe no banco de dados */
            Set<Professor> novosProfessores = request.getIdProfessores().stream()
                    .map(idProfessor -> professorRepository.findById(idProfessor)
                    .orElseThrow(() -> new ResourceNotFoundException("Professor com id " + idProfessor + " não encontrado."))) /* Lança exceção se não existir */
                    .collect(Collectors.toSet());

            aviso.setAvisosProfessores(novosProfessores);
        }

        if (request.getIdResponsaveis() != null) {
            /* Vê se cada responsável que receberá o aviso existe no banco de dados */
            Set<Responsavel> novosResponsaveis = request.getIdResponsaveis().stream()
                    .map(respId -> responsavelRepository.findById(respId)
                    .orElseThrow(() -> new ResourceNotFoundException("Responsável com id " + respId + " não encontrado."))) /* Lança exceção se não existir */
                    .collect(Collectors.toSet());

            aviso.setAvisosResponsaveis(novosResponsaveis);
        }

        /* Chama a função que salva o aviso atualizado no banco de dados */
        Aviso avisoAtualizado = avisoRepository.save(aviso);
        return avisoMapper.mappingResponse(avisoAtualizado);
    }

    /* Função que possui a lógica para exclusão de um aviso */
    public void delete(UUID id) {
        /* Vê se o aviso existe no banco de dados */
        if (!avisoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Aviso com id " + id + " não encontrado."); /* Lança exceção se não existir */
        }

        /* Chama a função para excluir o aviso do banco de dados */
        avisoRepository.deleteById(id);
    }
}
