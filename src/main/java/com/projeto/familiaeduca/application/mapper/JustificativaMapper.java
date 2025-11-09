package com.projeto.familiaeduca.application.mapper;

import com.projeto.familiaeduca.application.responses.JustificativaResponse;
import com.projeto.familiaeduca.domain.models.Justificativa;
import org.springframework.stereotype.Component;

@Component
public class JustificativaMapper {

    public JustificativaResponse mappingResponse(Justificativa justificativa) {
        JustificativaResponse response = new JustificativaResponse();
        response.setId(justificativa.getId());
        response.setData(justificativa.getData());
        response.setMotivo(justificativa.getMotivo());
        response.setAnexo(justificativa.getAnexo());

        if (justificativa.getAluno() != null) {
            JustificativaResponse.AlunoResumeResponse alunoResumo = new JustificativaResponse.AlunoResumeResponse();
            alunoResumo.setMatricula(justificativa.getAluno().getMatricula());
            alunoResumo.setNome(justificativa.getAluno().getNome());
            response.setAluno(alunoResumo);
        }

        if (justificativa.getResponsavel() != null) {
            JustificativaResponse.ResponsavelResumeResponse responsavelResumo = new JustificativaResponse.ResponsavelResumeResponse();
            responsavelResumo.setId(justificativa.getResponsavel().getId());
            responsavelResumo.setNome(justificativa.getResponsavel().getNome());
            response.setResponsavel(responsavelResumo);
        }

        return response;
    }
}