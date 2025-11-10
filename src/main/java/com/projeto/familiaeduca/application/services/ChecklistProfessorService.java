package com.projeto.familiaeduca.application.services;

import com.projeto.familiaeduca.application.exceptions.ResourceNotFoundException;
import com.projeto.familiaeduca.application.mapper.ChecklistProfessorMapper;
import com.projeto.familiaeduca.application.requests.CreateChecklistProfessorRequest;
import com.projeto.familiaeduca.application.requests.UpdateChecklistProfessorRequest;
import com.projeto.familiaeduca.application.responses.ChecklistProfessorResponse;
import com.projeto.familiaeduca.domain.models.Aluno;
import com.projeto.familiaeduca.domain.models.ChecklistProfessor;
import com.projeto.familiaeduca.domain.models.Professor;
import com.projeto.familiaeduca.infrastructure.repository.AlunoRepository;
import com.projeto.familiaeduca.infrastructure.repository.ChecklistProfessorRepository;
import com.projeto.familiaeduca.infrastructure.repository.ProfessorRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ChecklistProfessorService {

    /* Dependências para chamar Repositorys e Mapper */
    private final ChecklistProfessorRepository checklistProfessorRepository;
    private final AlunoRepository alunoRepository;
    private final ProfessorRepository professorRepository;
    private final ChecklistProfessorMapper checklistProfessorMapper;

    public ChecklistProfessorService(
        ChecklistProfessorRepository checklistProfessorRepository,
        AlunoRepository alunoRepository,
        ProfessorRepository professorRepository,
        ChecklistProfessorMapper checklistProfessorMapper
    ) {
        this.checklistProfessorRepository = checklistProfessorRepository;
        this.alunoRepository = alunoRepository;
        this.professorRepository = professorRepository;
        this.checklistProfessorMapper = checklistProfessorMapper;
    }

    /* Função que possui a lógica para criação de um checklist para professor */
    public ChecklistProfessorResponse create(CreateChecklistProfessorRequest request) {
        /* Vê se o aluno existe no banco de dados */
        Aluno aluno = alunoRepository.findById(request.getMatriculaAluno())
                .orElseThrow(() -> new ResourceNotFoundException("Aluno com matrícula " + request.getMatriculaAluno() + " não encontrado.")); /* Lança exceção se não existir */

        /* Vê se o professor existe no banco de dados */
        Professor professor = professorRepository.findById(request.getIdProfessor())
                .orElseThrow(() -> new ResourceNotFoundException("Professor com id " + request.getIdProfessor() + " não encontrado.")); /* Lança exceção se não existir */

        /* Cria o checklist e chama a função que salva no banco de dados */
        ChecklistProfessor checklist = new ChecklistProfessor();
        checklist.setDataChecklist(request.getDataChecklist());
        checklist.setObservacoes(request.getObservacoes());
        checklist.setAluno(aluno);
        checklist.setProfessor(professor);

        ChecklistProfessor novoChecklist = checklistProfessorRepository.save(checklist);
        return checklistProfessorMapper.mappingResponse(novoChecklist);
    }


    /* Função que possui a lógica para retornar a lista com todos os checklists cadastrados */
    public List<ChecklistProfessorResponse> getAll() {
        return checklistProfessorRepository.findAll().stream()
                .map(checklistProfessorMapper::mappingResponse)
                .collect(Collectors.toList());
    }

    /* Função que possui a lógica para encontrar um checklist cadastrado */
    public ChecklistProfessorResponse getById(UUID id) {
        /* Vê se o checklist existe no banco de dados */
        ChecklistProfessor checklist = checklistProfessorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Checklist de Professor com id " + id + " não encontrado.")); /* Lança exceção se não existir */

        return checklistProfessorMapper.mappingResponse(checklist);
    }

    /* Função que possui a lógica para atualizar as informações de um checklist */
    public ChecklistProfessorResponse update(UUID id, UpdateChecklistProfessorRequest request) {
        /* Vê se o checklist existe no banco de dados */
        ChecklistProfessor checklist = checklistProfessorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Checklist de Professor com id " + id + " não encontrado.")); /* Lança exceção se não existir */

        /* Confere se tudo está preenchido corretamente */
        if (request.getDataChecklist() != null) {
            checklist.setDataChecklist(request.getDataChecklist());
        }

        if (request.getObservacoes() != null) {
            checklist.setObservacoes(request.getObservacoes());
        }

        /* Chama a função que salva o checklist atualizado no banco de dados */
        ChecklistProfessor checklistAtualizado = checklistProfessorRepository.save(checklist);
        return checklistProfessorMapper.mappingResponse(checklistAtualizado);
    }

    /* Função que possui a lógica para exclusão de um checklist */
    public void delete(UUID id) {
        /* Vê se o boletim existe no banco de dados */
        if (!checklistProfessorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Checklist de Professor com id " + id + " não encontrado."); /* Lança exceção se não existir */
        }

        /* Chama a função para excluir o boletim do banco de dados */
        checklistProfessorRepository.deleteById(id);
    }
}
