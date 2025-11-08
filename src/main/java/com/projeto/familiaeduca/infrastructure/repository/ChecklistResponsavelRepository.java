package com.projeto.familiaeduca.infrastructure.repository;

import com.projeto.familiaeduca.domain.models.ChecklistResponsavel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ChecklistResponsavelRepository extends JpaRepository<ChecklistResponsavel, UUID> {
    List<ChecklistResponsavel> findByResponsavelIdAndAlunoMatricula(UUID idResponsavel, int matriculaAluno);
}