package com.projeto.familiaeduca.application.mapper;

import com.projeto.familiaeduca.application.responses.AvisoResponse;
import com.projeto.familiaeduca.domain.models.Aviso;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

/* Função que mapeia da classe AvisoResponse para Aviso */
@Component
public class AvisoMapper {
    public AvisoResponse mappingResponse(Aviso aviso) {
        AvisoResponse response = new AvisoResponse();
        response.setId(aviso.getId());
        response.setTitulo(aviso.getTitulo());
        response.setMensagem(aviso.getMensagem());
        response.setDataPublicacao(aviso.getDataPublicacao());

        if (aviso.getDiretor() != null) {
            AvisoResponse.DiretorResumeResponse diretorResumo = new AvisoResponse.DiretorResumeResponse();
            diretorResumo.setId(aviso.getDiretor().getId());
            diretorResumo.setNome(aviso.getDiretor().getNome());
            response.setDiretor(diretorResumo);
        }

        if (aviso.getAvisosProfessores() != null) {
            response.setProfessores(aviso.getAvisosProfessores().stream().map(professor -> {
                AvisoResponse.ProfessorResumeResponse resumo = new AvisoResponse.ProfessorResumeResponse();
                resumo.setId(professor.getId());
                resumo.setNome(professor.getNome());
                return resumo;
            }).collect(Collectors.toList()));
        }

        if (aviso.getAvisosResponsaveis() != null) {
            response.setResponsaveis(aviso.getAvisosResponsaveis().stream().map(responsavel -> {
                AvisoResponse.ResponsavelResumeResponse resumo = new AvisoResponse.ResponsavelResumeResponse();
                resumo.setId(responsavel.getId());
                resumo.setNome(responsavel.getNome());
                return resumo;
            }).collect(Collectors.toList()));
        }

        return response;
    }
}