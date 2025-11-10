package com.projeto.familiaeduca.application.mapper;

import com.projeto.familiaeduca.application.responses.CalendarioResponse;
import com.projeto.familiaeduca.domain.models.Calendario;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

/* Função que mapeia da classe CalendarioResponse para Calendario */
@Component
public class CalendarioMapper {
    public CalendarioResponse mappingResponse(Calendario calendario) {
        CalendarioResponse response = new CalendarioResponse();
        response.setId(calendario.getId());
        response.setDescricao(calendario.getDescricao());

        if (calendario.getDiretor() != null) {
            CalendarioResponse.DiretorResumeResponse diretorResumo = new CalendarioResponse.DiretorResumeResponse();
            diretorResumo.setId(calendario.getDiretor().getId());
            diretorResumo.setNome(calendario.getDiretor().getNome());
            response.setDiretor(diretorResumo);
        }

        if (calendario.getAvisosProfessores() != null) {
            response.setProfessoresNotificados(calendario.getAvisosProfessores().stream().map(professor -> {
                CalendarioResponse.ProfessorResumeResponse resumo = new CalendarioResponse.ProfessorResumeResponse();
                resumo.setId(professor.getId());
                resumo.setNome(professor.getNome());
                return resumo;
            }).collect(Collectors.toList()));
        }

        if (calendario.getAvisosResponsaveis() != null) {
            response.setResponsaveisNotificados(calendario.getAvisosResponsaveis().stream().map(responsavel -> {
                CalendarioResponse.ResponsavelResumeResponse resumo = new CalendarioResponse.ResponsavelResumeResponse();
                resumo.setId(responsavel.getId());
                resumo.setNome(responsavel.getNome());
                return resumo;
            }).collect(Collectors.toList()));
        }

        return response;
    }
}