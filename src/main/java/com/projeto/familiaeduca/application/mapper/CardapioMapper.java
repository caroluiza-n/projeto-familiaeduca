package com.projeto.familiaeduca.application.mapper;

import com.projeto.familiaeduca.application.responses.CardapioResponse;
import com.projeto.familiaeduca.domain.models.Cardapio;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class CardapioMapper {

    public CardapioResponse mappingResponse(Cardapio cardapio) {
        CardapioResponse response = new CardapioResponse();
        response.setId(cardapio.getId());
        response.setDescricao(cardapio.getDescricao());
        response.setDataPublicacao(cardapio.getDataPublicacao());

        if (cardapio.getDiretor() != null) {
            CardapioResponse.DiretorResumeResponse diretorResumo = new CardapioResponse.DiretorResumeResponse();
            diretorResumo.setId(cardapio.getDiretor().getId());
            diretorResumo.setNome(cardapio.getDiretor().getNome());
            response.setDiretor(diretorResumo);
        }

        if (cardapio.getAvisosProfessores() != null) {
            response.setProfessoresNotificados(cardapio.getAvisosProfessores().stream().map(professor -> {
                CardapioResponse.ProfessorResumeResponse resumo = new CardapioResponse.ProfessorResumeResponse();
                resumo.setId(professor.getId());
                resumo.setNome(professor.getNome());
                return resumo;
            }).collect(Collectors.toList()));
        }

        if (cardapio.getAvisosResponsaveis() != null) {
            response.setResponsaveisNotificados(cardapio.getAvisosResponsaveis().stream().map(responsavel -> {
                CardapioResponse.ResponsavelResumeResponse resumo = new CardapioResponse.ResponsavelResumeResponse();
                resumo.setId(responsavel.getId());
                resumo.setNome(responsavel.getNome());
                return resumo;
            }).collect(Collectors.toList()));
        }

        return response;
    }
}