package com.projeto.familiaeduca.application.services;

import com.projeto.familiaeduca.application.exceptions.ResourceNotFoundException;
import com.projeto.familiaeduca.application.mapper.ReuniaoMapper;
import com.projeto.familiaeduca.application.requests.CreateReuniaoRequest;
import com.projeto.familiaeduca.application.requests.UpdateReuniaoRequest;
import com.projeto.familiaeduca.application.responses.ReuniaoResponse;
import com.projeto.familiaeduca.domain.models.Responsavel;
import com.projeto.familiaeduca.domain.models.Reuniao;
import com.projeto.familiaeduca.domain.models.enums.StatusReuniao;
import com.projeto.familiaeduca.infrastructure.repository.ResponsavelRepository;
import com.projeto.familiaeduca.infrastructure.repository.ReuniaoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ReuniaoService {

    /* Dependências para chamar Repositorys e Mapper */
    private final ReuniaoRepository reuniaoRepository;
    private final ResponsavelRepository responsavelRepository;
    private final ReuniaoMapper reuniaoMapper;

    public ReuniaoService(
        ReuniaoRepository reuniaoRepository,
        ResponsavelRepository responsavelRepository,
        ReuniaoMapper reuniaoMapper
    ) {
        this.reuniaoRepository = reuniaoRepository;
        this.responsavelRepository = responsavelRepository;
        this.reuniaoMapper = reuniaoMapper;
    }

    /* Função que possui a lógica para criação de uma reunião */
    public ReuniaoResponse create(CreateReuniaoRequest request) {
        /* Vê se o responsável existe no banco de dados */
        Responsavel responsavel = responsavelRepository.findById(request.getIdResponsavel())
                .orElseThrow(() -> new ResourceNotFoundException("Responsável com id " + request.getIdResponsavel() + " não encontrado.")); /* Lança exceção se não existir */

        /* Cria a reunião e chama a função que salva no banco de dados */
        Reuniao reuniao = new Reuniao();
        reuniao.setData(request.getData());
        reuniao.setMotivo(request.getMotivo());
        reuniao.setStatus(StatusReuniao.AGENDADA);
        reuniao.setResponsavel(responsavel);

        Reuniao novaReuniao = reuniaoRepository.save(reuniao);
        return reuniaoMapper.mappingResponse(novaReuniao);
    }

    /* Função que possui a lógica para retornar a lista com todas as reuniões cadastradas */
    public List<ReuniaoResponse> getAll() {
        return reuniaoRepository.findAll().stream()
                .map(reuniaoMapper::mappingResponse)
                .collect(Collectors.toList());
    }

    /* Função que possui a lógica para encontrar uma reunião cadastrada */
    public ReuniaoResponse getById(UUID id) {
        /* Vê se a reunião existe no banco de dados */
        Reuniao reuniao = reuniaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reunião com id " + id + " não encontrada.")); /* Lança exceção se não existir */

        return reuniaoMapper.mappingResponse(reuniao);
    }

    /* Função que possui a lógica para retornar a lista de reuniões de um responsãvel */
    public List<ReuniaoResponse> getByResponsavel(UUID idResponsavel) {
        return reuniaoRepository.findByResponsavelId(idResponsavel).stream()
                .map(reuniaoMapper::mappingResponse)
                .collect(Collectors.toList());
    }

    /* Função que possui a lógica para atualizar as informações de uma reunião */
    public ReuniaoResponse update(UUID id, UpdateReuniaoRequest request) {
        /* Vê se a reunião existe no banco de dados */
        Reuniao reuniao = reuniaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reunião com id " + id + " não encontrada.")); /* Lança exceção se não existir */

        /* Confere se tudo está preenchido corretamente */
        if (request.getData() != null) {
            reuniao.setData(request.getData());
        }
        if (request.getMotivo() != null) {
            reuniao.setMotivo(request.getMotivo());
        }

        /* Chama a função que salva a reunião atualizada no banco de dados */
        Reuniao reuniaoAtualizada = reuniaoRepository.save(reuniao);
        return reuniaoMapper.mappingResponse(reuniaoAtualizada);
    }

    /* Função que possui a lógica para exclusão de uma reunião */
    public void delete(UUID id) {
        /* Vê se a reunião existe no banco de dados */
        if (!reuniaoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Reunião com id " + id + " não encontrada.");  /* Lança exceção se não existir */
        }

        /* Chama a função para excluir a reunião do banco de dados */
        reuniaoRepository.deleteById(id);
    }
}
