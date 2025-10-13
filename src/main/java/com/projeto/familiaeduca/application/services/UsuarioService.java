package com.projeto.familiaeduca.application.services;

import com.projeto.familiaeduca.application.exceptions.BusinessRuleException;
import com.projeto.familiaeduca.application.exceptions.DataIntegrityException;
import com.projeto.familiaeduca.application.exceptions.ResourceNotFoundException;
import com.projeto.familiaeduca.application.requests.*;
import com.projeto.familiaeduca.application.responses.UsuarioResponse;
import com.projeto.familiaeduca.domain.models.Diretor;
import com.projeto.familiaeduca.domain.models.Professor;
import com.projeto.familiaeduca.domain.models.Responsavel;
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

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UsuarioResponse> getAll() {
        List<Usuario> usuarios = usuarioRepository.findAll();

        return usuarios.stream()
                .map(this::mappingResponse)
                .collect(Collectors.toList());
    }

    public UsuarioResponse getById(UUID id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário com id " + id + " não encontrado."));

        return mappingResponse(usuario);
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

    private UsuarioResponse mappingResponse(Usuario usuario) {
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

    public UsuarioResponse createDiretor(CreateDiretorRequest request) {
        if(usuarioRepository.existsByEmail(request.getEmail())) {
            throw new DataIntegrityException("O e-mail informado já está em uso.");
        }

        Diretor diretor = new Diretor();

        diretor.setNome(request.getNome());
        diretor.setEmail(request.getEmail());
        diretor.setSenha(passwordEncoder.encode(request.getSenha()));
        diretor.setTelefone(request.getTelefone());

        Diretor novoDiretor = usuarioRepository.save(diretor);
        return mappingResponse(novoDiretor);
    }

    public UsuarioResponse updateDiretor(UUID id, UpdateDiretorRequest request) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário com id " + id + " não encontrado."));

        if (!(usuario instanceof Diretor)) {
            throw new BusinessRuleException("O usuário com id " + id + " não é um diretor.");
        }

        Diretor diretor = (Diretor) usuario;

        diretor.setNome(request.getNome());
        diretor.setEmail(request.getEmail());
        diretor.setTelefone(request.getTelefone());

        Diretor diretorAtualizado = usuarioRepository.save(diretor);

        return mappingResponse(diretorAtualizado);
    }

    public UsuarioResponse createProfessor(CreateProfessorRequest request) {
        if(usuarioRepository.existsByEmail(request.getEmail())) {
            throw new DataIntegrityException("O e-mail informado já está em uso.");
        }

        Professor professor = new Professor();

        professor.setNome(request.getNome());
        professor.setEmail(request.getEmail());
        professor.setSenha(passwordEncoder.encode(request.getSenha()));
        professor.setTelefone(request.getTelefone());

        Professor novoProfessor = usuarioRepository.save(professor);
        return mappingResponse(novoProfessor);
    }

    public UsuarioResponse updateProfessor(UUID id, UpdateProfessorRequest request) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário com id " + id + " não encontrado."));

        if(!(usuario instanceof Professor)) {
            throw new BusinessRuleException("O usuário com id " + id + " não é um professor.");
        }

        Professor professor = (Professor) usuario;

        professor.setNome(request.getNome());
        professor.setEmail(request.getEmail());
        professor.setTelefone(request.getTelefone());

        Professor professorAtualizado = usuarioRepository.save(professor);

        return mappingResponse(professorAtualizado);
    }

    public UsuarioResponse createResponsavel(CreateResponsavelRequest request) {
        if(usuarioRepository.existsByEmail(request.getEmail())) {
            throw new DataIntegrityException("O e-mail informado já está em uso.");
        }

        Responsavel responsavel = new Responsavel();

        responsavel.setNome(request.getNome());
        responsavel.setEmail(request.getEmail());
        responsavel.setSenha(passwordEncoder.encode(request.getSenha()));
        responsavel.setTelefone(request.getTelefone());
        responsavel.setEndereco(request.getEndereco());

        Responsavel novoResponsavel = usuarioRepository.save(responsavel);
        return mappingResponse(novoResponsavel);
    }

    public UsuarioResponse updateResponsavel(UUID id, UpdateResponsavelRequest request) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário com id " + id + " não encontrado."));

        if (!(usuario instanceof Responsavel)) {
            throw new BusinessRuleException("O usuário com id " + id + " não é um responsável.");
        }

        Responsavel responsavel = (Responsavel) usuario;

        responsavel.setNome(request.getNome());
        responsavel.setEmail(request.getEmail());
        responsavel.setTelefone(request.getTelefone());
        responsavel.setEndereco(request.getEndereco());

        Responsavel responsavelAtualizado = usuarioRepository.save(responsavel);

        return mappingResponse(responsavelAtualizado);
    }
}
