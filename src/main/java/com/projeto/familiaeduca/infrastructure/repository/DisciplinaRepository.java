package com.projeto.familiaeduca.infrastructure.repository;

import com.projeto.familiaeduca.domain.models.Disciplina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DisciplinaRepository extends JpaRepository<Disciplina, UUID> {
    List<Disciplina> findByNomeIn(List<String> disciplinas);
    List<Disciplina> findByPadraoTrue();
}
