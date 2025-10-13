package com.projeto.familiaeduca.application.services;

import com.projeto.familiaeduca.application.exceptions.BusinessRuleException;
import com.projeto.familiaeduca.application.exceptions.ResourceNotFoundException;
import com.projeto.familiaeduca.application.mapper.UsuarioMapper;
import com.projeto.familiaeduca.application.requests.*;
import com.projeto.familiaeduca.application.responses.UsuarioResponse;
import com.projeto.familiaeduca.domain.models.Usuario;
import com.projeto.familiaeduca.infrastructure.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioMapper  usuarioMapper;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.usuarioMapper = usuarioMapper;
    }

    public List<UsuarioResponse> getAll() {
        List<Usuario> usuarios = usuarioRepository.findAll();

        return usuarios.stream()
                .map(usuarioMapper::mappingResponse)
                .collect(Collectors.toList());
    }

    public UsuarioResponse getById(UUID id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário com id " + id + " não encontrado."));

        return usuarioMapper.mappingResponse(usuario);
    }

    public void updatePassword(UUID id, UpdatePasswordRequest request) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário com id " + id + " não encontrado."));

        if(!passwordEncoder.matches(request.getSenhaAntiga(), usuario.getSenha())) {
            throw new BusinessRuleException("A senha antiga está incorreta.");
        }

        String novaSenha = passwordEncoder.encode(request.getSenhaNova());
        usuario.setSenha(novaSenha);
        usuarioRepository.save(usuario);
    }

    public void delete(UUID id) {
        if(!usuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException(("Usuário com id " + id + " não encontrado."));
        }

        usuarioRepository.deleteById(id);
    }
}
