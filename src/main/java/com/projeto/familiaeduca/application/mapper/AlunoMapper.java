package com.projeto.familiaeduca.application.mapper;

import com.projeto.familiaeduca.application.responses.AlunoResponse;
import com.projeto.familiaeduca.domain.models.Aluno;
import org.springframework.stereotype.Component;

/* Função que mapeia da classe AlunoResponse para Aluno */
@Component
public class AlunoMapper {
    public AlunoResponse mappingResponse(Aluno aluno) {
        AlunoResponse response = new AlunoResponse();
        response.setMatricula(aluno.getMatricula());
        response.setNome(aluno.getNome());
        response.setDataNascimento(aluno.getDataNascimento());
        response.setLaudo(aluno.getLaudo());
        response.setAlergias(aluno.getAlergias());

        if (aluno.getTurma() != null) {
            AlunoResponse.TurmaResumeResponse turmaResumo = new AlunoResponse.TurmaResumeResponse();
            turmaResumo.setId(aluno.getTurma().getId());
            turmaResumo.setNome(aluno.getTurma().getNome());
            response.setTurma(turmaResumo);
        }

        if (aluno.getResponsavel() != null) {
            AlunoResponse.ResponsavelResumeResponse responsavelResumo = new AlunoResponse.ResponsavelResumeResponse();
            responsavelResumo.setId(aluno.getResponsavel().getId());
            responsavelResumo.setNome(aluno.getResponsavel().getNome());
            response.setResponsavel(responsavelResumo);
        }

        return response;
    }
}
