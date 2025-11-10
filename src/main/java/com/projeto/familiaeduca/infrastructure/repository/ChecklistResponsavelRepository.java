package com.projeto.familiaeduca.infrastructure.repository;

import com.projeto.familiaeduca.domain.models.ChecklistResponsavel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

/* Parte que interage com o banco de dados para informações sobre ChecklistResponsavel */
@Repository
public interface ChecklistResponsavelRepository extends JpaRepository<ChecklistResponsavel, UUID> {
    List<ChecklistResponsavel> findByResponsavelIdAndAlunoMatricula(UUID idResponsavel, int matriculaAluno);
}