package com.projeto.familiaeduca.application.mapper;

import com.projeto.familiaeduca.application.responses.UsuarioResponse;
import com.projeto.familiaeduca.domain.models.Diretor;
import com.projeto.familiaeduca.domain.models.Professor;
import com.projeto.familiaeduca.domain.models.Responsavel;
import com.projeto.familiaeduca.domain.models.Usuario;
import org.springframework.stereotype.Component;

/* Função que mapeia da classe UsuarioResponse para Usuario */
@Component
public class UsuarioMapper {
    public UsuarioResponse mappingResponse(Usuario usuario) {
        UsuarioResponse response = new UsuarioResponse();
        response.setId(usuario.getId());
        response.setNome(usuario.getNome());
        response.setEmail(usuario.getEmail());
        response.setTelefone(usuario.getTelefone());

        if(usuario instanceof Professor) {
            response.setFuncao("Professor");
        }
        if(usuario instanceof Diretor) {
            response.setFuncao("Diretor");
        }
        if(usuario instanceof Responsavel) {
            response.setFuncao("Responsavel");
        }

        return response;
    }
}
