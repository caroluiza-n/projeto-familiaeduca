package com.projeto.familiaeduca.application.mapper;

import com.projeto.familiaeduca.application.responses.RenovacaoMatriculaResponse;
import com.projeto.familiaeduca.domain.models.RenovacaoMatricula;
import org.springframework.stereotype.Component;

@Component
public class RenovacaoMatriculaMapper {

    public RenovacaoMatriculaResponse mappingResponse(RenovacaoMatricula renovacao) {
        RenovacaoMatriculaResponse response = new RenovacaoMatriculaResponse();
        response.setId(renovacao.getId());
        response.setAnoLetivo(renovacao.getAnoLetivo());
        response.setData(renovacao.getData());
        response.setStatus(renovacao.getStatus());

        if (renovacao.getAluno() != null) {
            RenovacaoMatriculaResponse.AlunoResumeResponse alunoResumo = new RenovacaoMatriculaResponse.AlunoResumeResponse();
            alunoResumo.setMatricula(renovacao.getAluno().getMatricula());
            alunoResumo.setNome(renovacao.getAluno().getNome());
            response.setAluno(alunoResumo);
        }

        if (renovacao.getResponsavel() != null) {
            RenovacaoMatriculaResponse.ResponsavelResumeResponse responsavelResumo = new RenovacaoMatriculaResponse.ResponsavelResumeResponse();
            responsavelResumo.setId(renovacao.getResponsavel().getId());
            responsavelResumo.setNome(renovacao.getResponsavel().getNome());
            response.setResponsavel(responsavelResumo);
        }

        return response;
    }
}