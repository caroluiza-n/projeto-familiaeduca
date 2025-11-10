package com.projeto.familiaeduca.infrastructure.repository;

import com.projeto.familiaeduca.domain.models.Boletim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

/* Parte que interage com o banco de dados para informações sobre Boletim */
@Repository
public interface BoletimRepository extends JpaRepository<Boletim, UUID> {
    List<Boletim> findByAlunoMatricula(int matricula);
}