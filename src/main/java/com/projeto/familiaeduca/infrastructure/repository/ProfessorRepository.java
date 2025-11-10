package com.projeto.familiaeduca.infrastructure.repository;

import com.projeto.familiaeduca.domain.models.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

/* Parte que interage com o banco de dados para informações sobre Professor */
@Repository
public interface ProfessorRepository extends JpaRepository<Professor, UUID> {
}
