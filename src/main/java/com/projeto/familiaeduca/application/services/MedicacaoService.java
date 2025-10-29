package com.projeto.familiaeduca.application.services;

import com.projeto.familiaeduca.application.exceptions.BusinessRuleException;
import com.projeto.familiaeduca.application.exceptions.ResourceNotFoundException;
import com.projeto.familiaeduca.application.mapper.MedicacaoMapper;
import com.projeto.familiaeduca.application.requests.CreateMedicacaoRequest;
import com.projeto.familiaeduca.application.responses.MedicacaoResponse;
import com.projeto.familiaeduca.domain.models.*;
import com.projeto.familiaeduca.infrastructure.repository.AlunoRepository;
import com.projeto.familiaeduca.infrastructure.repository.MedicacaoRepository;
import com.projeto.familiaeduca.infrastructure.repository.ProfessorRepository;
import com.projeto.familiaeduca.infrastructure.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.security.access.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MedicacaoService {
    private final MedicacaoRepository medicacaoRepository;
    private final AlunoRepository alunoRepository;
    private final ProfessorRepository professorRepository;
    private final MedicacaoMapper medicacaoMapper;
    private final UsuarioRepository usuarioRepository;

    public MedicacaoService(
        MedicacaoRepository medicacaoRepository,
        AlunoRepository alunoRepository,
        ProfessorRepository professorRepository,
        MedicacaoMapper medicacaoMapper,
        UsuarioRepository usuarioRepository) {
        this.medicacaoRepository = medicacaoRepository;
        this.alunoRepository = alunoRepository;
        this.professorRepository = professorRepository;
        this.medicacaoMapper = medicacaoMapper;
        this.usuarioRepository = usuarioRepository;
    }

    public MedicacaoResponse create(CreateMedicacaoRequest request, UserDetails userDetails) {
        Usuario usuario = usuarioRepository.findByEmail(userDetails.getUsername())
            .orElseThrow(() -> new ResourceNotFoundException("Usuário logado não encontrado."));

        if (!(usuario instanceof Professor || usuario instanceof Diretor)) {
            throw new AccessDeniedException("Apenas professores ou diretores podem registrar medicação.");
        }
        Professor professorAplicador = (Professor) usuario;

        Aluno aluno = alunoRepository.findById(request.getMatricula())
            .orElseThrow(() -> new ResourceNotFoundException("Aluno com matrícula " +  request.getMatricula() + "não encontrado."));

        if (usuario instanceof Professor && !aluno.getTurma().getProfessor().getId().equals(professorAplicador.getId())) {
            throw new BusinessRuleException("Permissão negada para registrar medicação desse aluno.");
        }

        Medicacao medicacao = new Medicacao();
        medicacao.setMedicamento(request.getMedicamento());
        medicacao.setDosagem(request.getDosagem());
        medicacao.setObservacoes(request.getObservacoes());
        medicacao.setDataAplicacao(LocalDateTime.now());
        medicacao.setAluno(aluno);
        medicacao.setProfessorAplicador(professorAplicador);

        return medicacaoMapper.mappingResponse(medicacaoRepository.save(medicacao));
    }

    public List<MedicacaoResponse> findByAluno(int matricula, UserDetails userDetails) {
        Usuario usuario = usuarioRepository.findByEmail(userDetails.getUsername())
            .orElseThrow(() -> new ResourceNotFoundException("Usuário logado não encontrado."));

        Aluno aluno = alunoRepository.findById(matricula)
            .orElseThrow(() -> new ResourceNotFoundException("Aluno com matrícula " + matricula + "não encontrado."));

        boolean access = false;
        if(usuario instanceof Diretor) {
            access = true;
        }
        else if(usuario instanceof Professor) {
            access = aluno.getTurma().getProfessor().getId().equals(usuario.getId());
        }
        else if(usuario instanceof Responsavel) {
            access = aluno.getResponsavel().getId().equals(usuario.getId());
        }

        if(!access) {
            throw new AccessDeniedException("Permissão negada para o acesso ao histórico do aluno.");
        }

        return medicacaoRepository.findByAluno_MatriculaOrderByDataAplicacaoDesc(matricula)
            .stream()
            .map(medicacaoMapper::mappingResponse)
            .collect(Collectors.toList());
    }

    public void delete(UUID id) {
        if(!medicacaoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Registro de medicação com id " + id + "não encontrado.");
        }
        medicacaoRepository.deleteById(id);
    }
}
