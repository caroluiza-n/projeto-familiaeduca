package com.projeto.familiaeduca.application.services;

import com.projeto.familiaeduca.application.exceptions.DataIntegrityException;
import com.projeto.familiaeduca.application.exceptions.ResourceNotFoundException;
import com.projeto.familiaeduca.application.mapper.UsuarioMapper;
import com.projeto.familiaeduca.application.requests.CreateResponsavelRequest;
import com.projeto.familiaeduca.application.requests.UpdateResponsavelRequest;
import com.projeto.familiaeduca.application.responses.UsuarioResponse;
import com.projeto.familiaeduca.domain.models.Responsavel;
import com.projeto.familiaeduca.infrastructure.repository.ResponsavelRepository;
import com.projeto.familiaeduca.infrastructure.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class ResponsavelService {

    private final ResponsavelRepository responsavelRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioMapper usuarioMapper;

    public ResponsavelService(
            ResponsavelRepository responsavelRepository,
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            UsuarioMapper usuarioMapper
    ) {
        this.responsavelRepository = responsavelRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.usuarioMapper = usuarioMapper;
    }
    public UsuarioResponse create(CreateResponsavelRequest request) {
        if(usuarioRepository.existsByEmail(request.getEmail())) {
            throw new DataIntegrityException("O e-mail informado já está em uso.");
        }

        Responsavel responsavel = new Responsavel();
        responsavel.setNome(request.getNome());
        responsavel.setEmail(request.getEmail());
        responsavel.setSenha(passwordEncoder.encode(request.getSenha()));
        responsavel.setTelefone(request.getTelefone());
        responsavel.setEndereco(request.getEndereco());

        Responsavel novoResponsavel = responsavelRepository.save(responsavel);
        return usuarioMapper.mappingResponse(novoResponsavel);
    }

    public UsuarioResponse update(UUID id, UpdateResponsavelRequest request) {
        Responsavel responsavel = responsavelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Responsável com id " + id + " não encontrado."));

        responsavel.setNome(request.getNome());
        responsavel.setEmail(request.getEmail());
        responsavel.setTelefone(request.getTelefone());
        responsavel.setEndereco(request.getEndereco());

        Responsavel responsavelAtualizado = responsavelRepository.save(responsavel);

        return usuarioMapper.mappingResponse(responsavelAtualizado);
    }
}
