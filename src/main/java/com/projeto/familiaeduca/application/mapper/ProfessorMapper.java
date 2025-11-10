package com.projeto.familiaeduca.application.mapper;

import com.projeto.familiaeduca.application.responses.ProfessorResponse;
import com.projeto.familiaeduca.domain.models.Professor;
import org.springframework.stereotype.Component;

/* Função que mapeia da classe ProfessorResponse para Professor */
@Component
public class ProfessorMapper {
    public ProfessorResponse mappingResponse(Professor professor) {
        ProfessorResponse response = new ProfessorResponse();
        response.setId(professor.getId());
        response.setNome(professor.getNome());
        response.setEmail(professor.getEmail());
        response.setTelefone(professor.getTelefone());

        if (professor.getTurma() != null) {
            ProfessorResponse.TurmaResumeResponse turmaResumo = new ProfessorResponse.TurmaResumeResponse();
            turmaResumo.setId(professor.getTurma().getId());
            turmaResumo.setNome(professor.getTurma().getNome());
            response.setTurma(turmaResumo);
        }

        return response;
    }
}