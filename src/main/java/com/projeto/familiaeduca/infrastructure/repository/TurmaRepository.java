package com.projeto.familiaeduca.infrastructure.repository;

import com.projeto.familiaeduca.domain.models.Turma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

/* Parte que interage com o banco de dados para informações sobre Turma */
@Repository
public interface TurmaRepository extends JpaRepository<Turma, UUID> {
    boolean existsByNome(String nome);
    boolean existsByProfessorId(UUID idProfessor);
}
