package com.projeto.familiaeduca.infrastructure.repository;

import com.projeto.familiaeduca.domain.models.Nota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NotaRepository extends JpaRepository<Nota, UUID> {
    List<Nota> findByAlunoMatriculaAndDisciplina(int matricula, String disciplina);
}