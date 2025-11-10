package com.projeto.familiaeduca.infrastructure.repository;

import com.projeto.familiaeduca.domain.models.RenovacaoMatricula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

/* Parte que interage com o banco de dados para informações sobre RenovacaoMatricula */
@Repository
public interface RenovacaoMatriculaRepository extends JpaRepository<RenovacaoMatricula, UUID> {
    Optional<RenovacaoMatricula> findByAlunoMatriculaAndAnoLetivo(int matricula, int anoLetivo);
}