package com.projeto.familiaeduca.infrastructure.repository;

import com.projeto.familiaeduca.domain.models.Boletim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface BoletimRepository extends JpaRepository<Boletim, UUID> {
    List<Boletim> findByAlunoMatricula(int matricula);
}