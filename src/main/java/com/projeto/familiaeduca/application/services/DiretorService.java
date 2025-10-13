package com.projeto.familiaeduca.application.services;

import com.projeto.familiaeduca.application.exceptions.DataIntegrityException;
import com.projeto.familiaeduca.application.exceptions.ResourceNotFoundException;
import com.projeto.familiaeduca.application.mapper.UsuarioMapper;
import com.projeto.familiaeduca.application.requests.CreateDiretorRequest;
import com.projeto.familiaeduca.application.requests.UpdateDiretorRequest;
import com.projeto.familiaeduca.application.responses.UsuarioResponse;
import com.projeto.familiaeduca.domain.models.Diretor;
import com.projeto.familiaeduca.infrastructure.repository.DiretorRepository;
import com.projeto.familiaeduca.infrastructure.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class DiretorService {

    private final DiretorRepository diretorRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioMapper usuarioMapper;

    public DiretorService(
            DiretorRepository diretorRepository,
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            UsuarioMapper usuarioMapper
    ) {
        this.diretorRepository = diretorRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.usuarioMapper = usuarioMapper;
    }

    public UsuarioResponse create(CreateDiretorRequest request) {
        if(usuarioRepository.existsByEmail(request.getEmail())) {
            throw new DataIntegrityException("O e-mail informado já está em uso.");
        }

        Diretor diretor = new Diretor();
        diretor.setNome(request.getNome());
        diretor.setEmail(request.getEmail());
        diretor.setSenha(passwordEncoder.encode(request.getSenha()));
        diretor.setTelefone(request.getTelefone());

        Diretor novoDiretor = diretorRepository.save(diretor);
        return usuarioMapper.mappingResponse(novoDiretor);
    }

    public UsuarioResponse update(UUID id, UpdateDiretorRequest request) {
        Diretor diretor = diretorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Diretor com id " + id + " não encontrado."));

        diretor.setNome(request.getNome());
        diretor.setEmail(request.getEmail());
        diretor.setTelefone(request.getTelefone());

        Diretor diretorAtualizado = diretorRepository.save(diretor);

        return usuarioMapper.mappingResponse(diretorAtualizado);
    }
}
