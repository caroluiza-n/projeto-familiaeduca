package com.projeto.familiaeduca.application.mapper;

import com.projeto.familiaeduca.application.responses.FrequenciaResponse;
import com.projeto.familiaeduca.domain.models.Frequencia;
import org.springframework.stereotype.Component;

@Component
public class FrequenciaMapper {

    public FrequenciaResponse mappingResponse(Frequencia frequencia) {
        FrequenciaResponse response = new FrequenciaResponse();
        response.setId(frequencia.getId());
        response.setData(frequencia.getData());
        response.setPresente(frequencia.isPresente());

        if (frequencia.getAluno() != null) {
            FrequenciaResponse.AlunoResumeResponse alunoResumo = new FrequenciaResponse.AlunoResumeResponse();
            alunoResumo.setMatricula(frequencia.getAluno().getMatricula());
            alunoResumo.setNome(frequencia.getAluno().getNome());
            response.setAluno(alunoResumo);
        }

        if (frequencia.getTurma() != null) {
            FrequenciaResponse.TurmaResumeResponse turmaResumo = new FrequenciaResponse.TurmaResumeResponse();
            turmaResumo.setId(frequencia.getTurma().getId());
            turmaResumo.setNome(frequencia.getTurma().getNome());
            response.setTurma(turmaResumo);
        }

        return response;
    }
}