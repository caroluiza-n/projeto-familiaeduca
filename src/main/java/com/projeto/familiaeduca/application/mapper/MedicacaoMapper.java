package com.projeto.familiaeduca.application.mapper;

import com.projeto.familiaeduca.application.responses.MedicacaoResponse;
import com.projeto.familiaeduca.domain.models.Medicacao;
import org.springframework.stereotype.Component;

/* Função que mapeia da classe MedicacaoResponse para Medicacao */
@Component
public class MedicacaoMapper {
    public MedicacaoResponse mappingResponse(Medicacao medicacao) {
        MedicacaoResponse response = new MedicacaoResponse();
        response.setId(medicacao.getId());
        response.setMedicamento(medicacao.getMedicamento());
        response.setDosagem(medicacao.getDosagem());
        response.setObservacoes(medicacao.getObservacoes());

        if(medicacao.getAluno() != null) {
            MedicacaoResponse.AlunoResumeResponse alunoResumo = new MedicacaoResponse.AlunoResumeResponse();
            alunoResumo.setMatricula(medicacao.getAluno().getMatricula());
            alunoResumo.setNome(medicacao.getAluno().getNome());
            response.setAluno(alunoResumo);
        }

        if(medicacao.getProfessorAplicador() != null) {
            MedicacaoResponse.ProfessorResumeResponse professorResumo = new MedicacaoResponse.ProfessorResumeResponse();
            professorResumo.setId(medicacao.getProfessorAplicador().getId());
            professorResumo.setNome(medicacao.getProfessorAplicador().getNome());
        }

        return response;
    }
}
