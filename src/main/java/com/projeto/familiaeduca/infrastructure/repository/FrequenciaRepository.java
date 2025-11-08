package com.projeto.familiaeduca.infrastructure.repository;

import com.projeto.familiaeduca.domain.models.Frequencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface FrequenciaRepository extends JpaRepository<Frequencia, UUID> {
    Optional<Frequencia> findByAlunoMatriculaAndData(int matricula, LocalDate data);
}