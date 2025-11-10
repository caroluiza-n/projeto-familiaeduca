package com.projeto.familiaeduca.infrastructure.repository;

import com.projeto.familiaeduca.domain.models.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/* Parte que interage com o banco de dados para informações sobre Aluno */
@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Integer> {
    boolean existsById(int matricula);
}
