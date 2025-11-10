package com.projeto.familiaeduca.application.mapper;

import com.projeto.familiaeduca.application.responses.BoletimResponse;
import com.projeto.familiaeduca.domain.models.Boletim;
import org.springframework.stereotype.Component;

/* Função que mapeia da classe BoletimResponse para Boletim */
@Component
public class BoletimMapper {
    public BoletimResponse mappingResponse(Boletim boletim) {
        BoletimResponse response = new BoletimResponse();
        response.setId(boletim.getId());
        response.setBimestre(boletim.getBimestre());
        response.setAno(boletim.getAno());
        response.setObservacoes(boletim.getObservacoes());

        if (boletim.getAluno() != null) {
            BoletimResponse.AlunoResumeResponse alunoResumo = new BoletimResponse.AlunoResumeResponse();
            alunoResumo.setMatricula(boletim.getAluno().getMatricula());
            alunoResumo.setNome(boletim.getAluno().getNome());
            response.setAluno(alunoResumo);
        }

        if (boletim.getDiretor() != null) {
            BoletimResponse.DiretorResumeResponse diretorResumo = new BoletimResponse.DiretorResumeResponse();
            diretorResumo.setId(boletim.getDiretor().getId());
            diretorResumo.setNome(boletim.getDiretor().getNome());
            response.setDiretor(diretorResumo);
        }

        return response;
    }
}