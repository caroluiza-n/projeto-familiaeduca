package com.projeto.familiaeduca.application.mapper;

import com.projeto.familiaeduca.application.responses.NotaResponse;
import com.projeto.familiaeduca.domain.models.Nota;
import org.springframework.stereotype.Component;

/* Função que mapeia da classe NotaResponse para Nota */
@Component
public class NotaMapper {
    public NotaResponse mappingResponse(Nota nota) {
        NotaResponse response = new NotaResponse();
        response.setId(nota.getId());
        response.setDisciplina(nota.getDisciplina());
        response.setNota(nota.getNota());
        response.setDataAvaliacao(nota.getDataAvaliacao());

        if (nota.getAluno() != null) {
            NotaResponse.AlunoResumeResponse alunoResumo = new NotaResponse.AlunoResumeResponse();
            alunoResumo.setMatricula(nota.getAluno().getMatricula());
            alunoResumo.setNome(nota.getAluno().getNome());
            response.setAluno(alunoResumo);
        }

        if (nota.getTurma() != null) {
            NotaResponse.TurmaResumeResponse turmaResumo = new NotaResponse.TurmaResumeResponse();
            turmaResumo.setId(nota.getTurma().getId());
            turmaResumo.setNome(nota.getTurma().getNome());
            response.setTurma(turmaResumo);
        }

        return response;
    }
}