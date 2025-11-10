package com.projeto.familiaeduca.application.mapper;

import com.projeto.familiaeduca.application.responses.ReuniaoResponse;
import com.projeto.familiaeduca.domain.models.Reuniao;
import org.springframework.stereotype.Component;

/* Função que mapeia da classe ReuniaoResponse para Reuniao */
@Component
public class ReuniaoMapper {
    public ReuniaoResponse mappingResponse(Reuniao reuniao) {
        ReuniaoResponse response = new ReuniaoResponse();
        response.setId(reuniao.getId());
        response.setData(reuniao.getData());
        response.setMotivo(reuniao.getMotivo());
        response.setStatus(reuniao.getStatus());

        if (reuniao.getResponsavel() != null) {
            ReuniaoResponse.ResponsavelResumeResponse responsavelResumo = new ReuniaoResponse.ResponsavelResumeResponse();
            responsavelResumo.setId(reuniao.getResponsavel().getId());
            responsavelResumo.setNome(reuniao.getResponsavel().getNome());
            response.setResponsavel(responsavelResumo);
        }

        return response;
    }
}