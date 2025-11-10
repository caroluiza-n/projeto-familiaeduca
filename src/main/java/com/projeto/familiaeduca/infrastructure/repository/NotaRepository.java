package com.projeto.familiaeduca.infrastructure.repository;

import com.projeto.familiaeduca.domain.models.Nota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

/* Parte que interage com o banco de dados para informações sobre Nota */
@Repository
public interface NotaRepository extends JpaRepository<Nota, UUID> {
    List<Nota> findByAlunoMatricula(int matricula);
    List<Nota> findByDisciplinaId(UUID disciplinaId);
    List<Nota> findByAlunoMatriculaAndDisciplinaId(int matricula, UUID idDisciplina);
}