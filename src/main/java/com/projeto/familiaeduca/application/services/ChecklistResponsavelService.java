package com.projeto.familiaeduca.application.services;

import com.projeto.familiaeduca.application.exceptions.ResourceNotFoundException;
import com.projeto.familiaeduca.application.mapper.ChecklistResponsavelMapper;
import com.projeto.familiaeduca.application.requests.CreateChecklistResponsavelRequest;
import com.projeto.familiaeduca.application.requests.UpdateChecklistResponsavelRequest;
import com.projeto.familiaeduca.application.responses.ChecklistResponsavelResponse;
import com.projeto.familiaeduca.domain.models.Aluno;
import com.projeto.familiaeduca.domain.models.ChecklistResponsavel;
import com.projeto.familiaeduca.domain.models.Responsavel;
import com.projeto.familiaeduca.infrastructure.repository.AlunoRepository;
import com.projeto.familiaeduca.infrastructure.repository.ChecklistResponsavelRepository;
import com.projeto.familiaeduca.infrastructure.repository.ResponsavelRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ChecklistResponsavelService {

    /* Dependências para chamar Repositorys e Mapper */
    private final ChecklistResponsavelRepository checklistResponsavelRepository;
    private final AlunoRepository alunoRepository;
    private final ResponsavelRepository responsavelRepository;
    private final ChecklistResponsavelMapper checklistResponsavelMapper;

    public ChecklistResponsavelService(
        ChecklistResponsavelRepository checklistResponsavelRepository,
        AlunoRepository alunoRepository,
        ResponsavelRepository responsavelRepository,
        ChecklistResponsavelMapper checklistResponsavelMapper
    ) {
        this.checklistResponsavelRepository = checklistResponsavelRepository;
        this.alunoRepository = alunoRepository;
        this.responsavelRepository = responsavelRepository;
        this.checklistResponsavelMapper = checklistResponsavelMapper;
    }

    /* Endpoint para inserir um registro de checklist */
    public ChecklistResponsavelResponse create(CreateChecklistResponsavelRequest request) {
        /* Vê se o aluno existe no banco de dados */
        Aluno aluno = alunoRepository.findById(request.getMatriculaAluno())
                .orElseThrow(() -> new ResourceNotFoundException("Aluno com matrícula " + request.getMatriculaAluno() + " não encontrado.")); /* Lança exceção se não existir */

        /* Vê se o responsável existe no banco de dados */
        Responsavel responsavel = responsavelRepository.findById(request.getIdResponsavel())
                .orElseThrow(() -> new ResourceNotFoundException("Responsável com id " + request.getIdResponsavel() + " não encontrado.")); /* Lança exceção se não existir */

        /* Cria o checklist e chama a função que salva no banco de dados */
        ChecklistResponsavel checklist = new ChecklistResponsavel();
        checklist.setDataChecklist(request.getDataChecklist());
        checklist.setItensVerificados(request.getItensVerificados());
        checklist.setAluno(aluno);
        checklist.setResponsavel(responsavel);

        ChecklistResponsavel novoChecklist = checklistResponsavelRepository.save(checklist);
        return checklistResponsavelMapper.mappingResponse(novoChecklist);
    }

    /* Função que possui a lógica para retornar a lista com todos os checklists cadastrados */
    public List<ChecklistResponsavelResponse> getAll() {
        return checklistResponsavelRepository.findAll().stream()
                .map(checklistResponsavelMapper::mappingResponse)
                .collect(Collectors.toList());
    }

    /* Função que possui a lógica para encontrar um checklist cadastrado */
    public ChecklistResponsavelResponse getById(UUID id) {
        /* Vê se o checklist existe no banco de dados */
        ChecklistResponsavel checklist = checklistResponsavelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Checklist de Responsável com id " + id + " não encontrado.")); /* Lança exceção se não existir */

        return checklistResponsavelMapper.mappingResponse(checklist);
    }

    /* Função que possui a lógica para atualizar as informações de um checklist */
    public ChecklistResponsavelResponse update(UUID id, UpdateChecklistResponsavelRequest request) {
        /* Vê se o checklist existe no banco de dados */
        ChecklistResponsavel checklist = checklistResponsavelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Checklist de Responsável com id " + id + " não encontrado.")); /* Lança exceção se não existir */

        /* Confere se tudo está preenchido corretamente */
        if (request.getItensVerificados() != null) {
            checklist.setItensVerificados(request.getItensVerificados());
        }

        /* Chama a função que salva o checklist atualizado no banco de dados */
        ChecklistResponsavel checklistAtualizado = checklistResponsavelRepository.save(checklist);
        return checklistResponsavelMapper.mappingResponse(checklistAtualizado);
    }

    /* Função que possui a lógica para exclusão de um checklist */
    public void delete(UUID id) {
        /* Vê se o boletim existe no banco de dados */
        if (!checklistResponsavelRepository.existsById(id)) {
            throw new ResourceNotFoundException("Checklist de Responsável com id " + id + " não encontrado."); /* Lança exceção se não existir */
        }

        /* Chama a função para excluir o boletim do banco de dados */
        checklistResponsavelRepository.deleteById(id);
    }
}
