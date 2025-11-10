package com.projeto.familiaeduca.infrastructure.repository;

import com.projeto.familiaeduca.domain.models.Frequencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.time.LocalDate;
import java.util.Optional;

/* Parte que interage com o banco de dados para informações sobre Frequencia */
@Repository
public interface FrequenciaRepository extends JpaRepository<Frequencia, UUID> {
    List<Frequencia> findByAlunoMatricula(int matricula);
    Optional<Frequencia> findByAlunoMatriculaAndData(int matricula, LocalDate data);
    List<Frequencia> findByTurmaIdAndData(UUID turmaId, LocalDate data);
}