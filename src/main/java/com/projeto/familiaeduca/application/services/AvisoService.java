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
            AvisoMapper avisoMapper) {
        this.avisoRepository = avisoRepository;
        this.diretorRepository = diretorRepository;
        this.professorRepository = professorRepository;
        this.responsavelRepository = responsavelRepository;
        this.avisoMapper = avisoMapper;
    }

    public AvisoResponse create(CreateAvisoRequest request) {

        Diretor diretor = diretorRepository.findById(request.getIdDiretor())
                .orElseThrow(() -> new ResourceNotFoundException("Diretor com ID " + request.getIdDiretor() + " não encontrado."));


        Set<Professor> professores = request.getIdsProfessores().stream()
                .map(id -> professorRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Professor com ID " + id + " não encontrado.")))
                .collect(Collectors.toSet());


        Set<Responsavel> responsaveis = request.getIdsResponsaveis().stream()
                .map(id -> responsavelRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Responsável com ID " + id + " não encontrado.")))
                .collect(Collectors.toSet());


        Aviso aviso = new Aviso();
        aviso.setTitulo(request.getTitulo());
        aviso.setMensagem(request.getMensagem());
        aviso.setDataPublicacao(LocalDate.now()); // Data de publicação é hoje
        aviso.setDiretor(diretor);
        aviso.setAvisosProfessores(professores);
        aviso.setAvisosResponsaveis(responsaveis);


        Aviso novoAviso = avisoRepository.save(aviso);
        return avisoMapper.mappingResponse(novoAviso);
    }

    public List<AvisoResponse> getAll() {

        return avisoRepository.findAll().stream()
                .map(avisoMapper::mappingResponse)
                .collect(Collectors.toList());
    }


    public AvisoResponse getById(UUID id) {
        Aviso aviso = avisoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aviso com ID " + id + " não encontrado."));

        return avisoMapper.mappingResponse(aviso);
    }

    public AvisoResponse update(UUID id, UpdateAvisoRequest request) {

        Aviso aviso = avisoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aviso com ID " + id + " não encontrado."));


        if (request.getTitulo() != null) {
            aviso.setTitulo(request.getTitulo());
        }
        if (request.getMensagem() != null) {
            aviso.setMensagem(request.getMensagem());
        }


        if (request.getIdsProfessores() != null) {
            Set<Professor> novosProfessores = request.getIdsProfessores().stream()
                    .map(profId -> professorRepository.findById(profId)
                            .orElseThrow(() -> new ResourceNotFoundException("Professor com ID " + profId + " não encontrado para atualização.")))
                    .collect(Collectors.toSet());
            aviso.setAvisosProfessores(novosProfessores);
        }


        if (request.getIdsResponsaveis() != null) {
            Set<Responsavel> novosResponsaveis = request.getIdsResponsaveis().stream()
                    .map(respId -> responsavelRepository.findById(respId)
                            .orElseThrow(() -> new ResourceNotFoundException("Responsável com ID " + respId + " não encontrado para atualização.")))
                    .collect(Collectors.toSet());
            aviso.setAvisosResponsaveis(novosResponsaveis);
        }


        Aviso avisoAtualizado = avisoRepository.save(aviso);
        return avisoMapper.mappingResponse(avisoAtualizado);
    }
}
