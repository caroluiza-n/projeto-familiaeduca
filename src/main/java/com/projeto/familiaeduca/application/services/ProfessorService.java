package com.projeto.familiaeduca.application.services;

import com.projeto.familiaeduca.application.exceptions.DataIntegrityException;
import com.projeto.familiaeduca.application.exceptions.ResourceNotFoundException;
import com.projeto.familiaeduca.application.mapper.UsuarioMapper;
import com.projeto.familiaeduca.application.requests.CreateProfessorRequest;
import com.projeto.familiaeduca.application.requests.UpdateProfessorRequest;
import com.projeto.familiaeduca.application.responses.UsuarioResponse;
import com.projeto.familiaeduca.domain.models.Professor;
import com.projeto.familiaeduca.infrastructure.repository.ProfessorRepository;
import com.projeto.familiaeduca.infrastructure.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class ProfessorService {

    private final ProfessorRepository professorRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioMapper usuarioMapper;

    public ProfessorService(
            ProfessorRepository professorRepository,
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            UsuarioMapper usuarioMapper
    ) {
        this.professorRepository = professorRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.usuarioMapper = usuarioMapper;
    }

    public UsuarioResponse create(CreateProfessorRequest request) {
        if(usuarioRepository.existsByEmail(request.getEmail())) {
            throw new DataIntegrityException("O e-mail informado já está em uso.");
        }

        Professor professor = new Professor();
        professor.setNome(request.getNome());
        professor.setEmail(request.getEmail());
        professor.setSenha(passwordEncoder.encode(request.getSenha()));
        professor.setTelefone(request.getTelefone());

        Professor novoProfessor = professorRepository.save(professor);
        return usuarioMapper.mappingResponse(novoProfessor);
    }

    public UsuarioResponse update(UUID id, UpdateProfessorRequest request) {
        Professor professor = professorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Professor com id " + id + " não encontrado."));

        professor.setNome(request.getNome());
        professor.setEmail(request.getEmail());
        professor.setTelefone(request.getTelefone());

        Professor professorAtualizado = professorRepository.save(professor);

        return usuarioMapper.mappingResponse(professorAtualizado);
    }
}
