package com.projeto.familiaeduca.application.services;

import com.projeto.familiaeduca.application.exceptions.ResourceNotFoundException;
import com.projeto.familiaeduca.application.mapper.ReuniaoMapper;
import com.projeto.familiaeduca.application.requests.CreateReuniaoRequest;
import com.projeto.familiaeduca.application.requests.UpdateReuniaoRequest;
import com.projeto.familiaeduca.application.responses.ReuniaoResponse;
import com.projeto.familiaeduca.domain.models.Reuniao;
import com.projeto.familiaeduca.domain.models.Responsavel;
import com.projeto.familiaeduca.infrastructure.repository.ReuniaoRepository;
import com.projeto.familiaeduca.infrastructure.repository.ResponsavelRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ReuniaoService {

    private final ReuniaoRepository reuniaoRepository;
    private final ResponsavelRepository responsavelRepository;
    private final ReuniaoMapper reuniaoMapper;

    public ReuniaoService(
            ReuniaoRepository reuniaoRepository,
            ResponsavelRepository responsavelRepository,
            ReuniaoMapper reuniaoMapper) {
        this.reuniaoRepository = reuniaoRepository;
        this.responsavelRepository = responsavelRepository;
        this.reuniaoMapper = reuniaoMapper;
    }

    public ReuniaoResponse create(CreateReuniaoRequest request) {
        Responsavel responsavel = responsavelRepository.findById(request.getIdResponsavel())
                .orElseThrow(() -> new ResourceNotFoundException("Responsável com ID " + request.getIdResponsavel() + " não encontrado."));

        Reuniao reuniao = new Reuniao();
        reuniao.setData(request.getData());
        reuniao.setMotivo(request.getMotivo());
        reuniao.setResponsavel(responsavel);

        Reuniao novaReuniao = reuniaoRepository.save(reuniao);
        return reuniaoMapper.mappingResponse(novaReuniao);
    }

    public List<ReuniaoResponse> getAll() {
        return reuniaoRepository.findAll().stream()
                .map(reuniaoMapper::mappingResponse)
                .collect(Collectors.toList());
    }

    public ReuniaoResponse getById(UUID id) {
        Reuniao reuniao = reuniaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reunião com ID " + id + " não encontrada."));

        return reuniaoMapper.mappingResponse(reuniao);
    }

    public ReuniaoResponse update(UUID id, UpdateReuniaoRequest request) {
        Reuniao reuniao = reuniaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reunião com ID " + id + " não encontrada."));

        if (request.getData() != null) {
            reuniao.setData(request.getData());
        }
        if (request.getMotivo() != null) {
            reuniao.setMotivo(request.getMotivo());
        }

        Reuniao reuniaoAtualizada = reuniaoRepository.save(reuniao);
        return reuniaoMapper.mappingResponse(reuniaoAtualizada);
    }

    public void delete(UUID id) {
        if (!reuniaoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Reunião com ID " + id + " não encontrada.");
        }
        reuniaoRepository.deleteById(id);
    }
}