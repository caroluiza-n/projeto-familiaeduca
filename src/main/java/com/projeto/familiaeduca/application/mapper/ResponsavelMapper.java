package com.projeto.familiaeduca.application.mapper;

import com.projeto.familiaeduca.application.responses.ResponsavelResponse;
import com.projeto.familiaeduca.domain.models.Responsavel;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class ResponsavelMapper {

    public ResponsavelResponse mappingResponse(Responsavel responsavel) {
        ResponsavelResponse response = new ResponsavelResponse();
        response.setId(responsavel.getId());
        response.setNome(responsavel.getNome());
        response.setEmail(responsavel.getEmail());
        response.setTelefone(responsavel.getTelefone());

        response.setEndereco(responsavel.getEndereco());

        if (responsavel.getAlunos() != null) {
            response.setAlunos(responsavel.getAlunos().stream().map(aluno -> {
                ResponsavelResponse.AlunoResumeResponse alunoResumo = new ResponsavelResponse.AlunoResumeResponse();
                alunoResumo.setMatricula(aluno.getMatricula());
                alunoResumo.setNome(aluno.getNome());
                return alunoResumo;
            }).collect(Collectors.toList()));
        }

        return response;
    }
}