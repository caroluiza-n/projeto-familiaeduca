package com.projeto.familiaeduca.infrastructure.repository;

import com.projeto.familiaeduca.domain.models.ChecklistProfessor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.Optional;
import java.util.List;
import java.util.UUID;

/* Parte que interage com o banco de dados para informações sobre ChecklistProfessor */
@Repository
public interface ChecklistProfessorRepository extends JpaRepository<ChecklistProfessor, UUID> {
    List<ChecklistProfessor> findByProfessorIdAndAlunoMatricula(UUID idProfessor, int matriculaAluno);
    Optional<ChecklistProfessor> findFirstByAlunoMatriculaAndDataChecklist(Integer matricula, LocalDate data);
}