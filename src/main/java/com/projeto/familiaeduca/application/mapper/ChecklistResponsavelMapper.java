package com.projeto.familiaeduca.application.mapper;

import com.projeto.familiaeduca.application.responses.ChecklistResponsavelResponse;
import com.projeto.familiaeduca.domain.models.ChecklistResponsavel;
import org.springframework.stereotype.Component;

@Component
public class ChecklistResponsavelMapper {

    public ChecklistResponsavelResponse mappingResponse(ChecklistResponsavel checklist) {
        ChecklistResponsavelResponse response = new ChecklistResponsavelResponse();
        response.setId(checklist.getId());
        response.setDataChecklist(checklist.getDataChecklist());
        response.setItensVerificados(checklist.getItensVerificados());

        if (checklist.getAluno() != null) {
            ChecklistResponsavelResponse.AlunoResumeResponse alunoResumo = new ChecklistResponsavelResponse.AlunoResumeResponse();
            alunoResumo.setMatricula(checklist.getAluno().getMatricula());
            alunoResumo.setNome(checklist.getAluno().getNome());
            response.setAluno(alunoResumo);
        }

        if (checklist.getResponsavel() != null) {
            ChecklistResponsavelResponse.ResponsavelResumeResponse responsavelResumo = new ChecklistResponsavelResponse.ResponsavelResumeResponse();
            responsavelResumo.setId(checklist.getResponsavel().getId());
            responsavelResumo.setNome(checklist.getResponsavel().getNome());
            response.setResponsavel(responsavelResumo);
        }

        return response;
    }
}