package com.projeto.familiaeduca.infrastructure.repository;

import com.projeto.familiaeduca.domain.models.Calendario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

/* Parte que interage com o banco de dados para informações sobre Calendario */
@Repository
public interface CalendarioRepository extends JpaRepository<Calendario, UUID> {
}