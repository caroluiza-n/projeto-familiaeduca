package com.projeto.familiaeduca.application.mapper;

import com.projeto.familiaeduca.application.responses.ChecklistProfessorResponse;
import com.projeto.familiaeduca.domain.models.ChecklistProfessor;
import org.springframework.stereotype.Component;

/* Função que mapeia da classe ChecklistProfessorResponse para ChecklistProfessor */
@Component
public class ChecklistProfessorMapper {
    public ChecklistProfessorResponse mappingResponse(ChecklistProfessor checklist) {
        ChecklistProfessorResponse response = new ChecklistProfessorResponse();
        response.setId(checklist.getId());
        response.setDataChecklist(checklist.getDataChecklist());
        response.setObservacoes(checklist.getObservacoes());

        if (checklist.getAluno() != null) {
            ChecklistProfessorResponse.AlunoResumeResponse alunoResumo = new ChecklistProfessorResponse.AlunoResumeResponse();
            alunoResumo.setMatricula(checklist.getAluno().getMatricula());
            alunoResumo.setNome(checklist.getAluno().getNome());
            response.setAluno(alunoResumo);
        }

        if (checklist.getProfessor() != null) {
            ChecklistProfessorResponse.ProfessorResumeResponse professorResumo = new ChecklistProfessorResponse.ProfessorResumeResponse();
            professorResumo.setId(checklist.getProfessor().getId());
            professorResumo.setNome(checklist.getProfessor().getNome());
            response.setProfessor(professorResumo);
        }

        return response;
    }
}