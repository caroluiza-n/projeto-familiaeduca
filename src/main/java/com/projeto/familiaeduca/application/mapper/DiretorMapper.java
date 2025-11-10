package com.projeto.familiaeduca.application.mapper;

import com.projeto.familiaeduca.application.responses.DiretorResponse;
import com.projeto.familiaeduca.domain.models.Diretor;
import org.springframework.stereotype.Component;

/* Função que mapeia da classe DiretorResponse para Diretor */
@Component
public class DiretorMapper {
    public DiretorResponse mappingResponse(Diretor diretor) {
        DiretorResponse response = new DiretorResponse();
        response.setId(diretor.getId());
        response.setNome(diretor.getNome());
        response.setEmail(diretor.getEmail());
        response.setTelefone(diretor.getTelefone());
        return response;
    }
}