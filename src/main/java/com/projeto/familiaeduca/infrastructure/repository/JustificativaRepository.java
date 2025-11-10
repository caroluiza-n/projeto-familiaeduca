package com.projeto.familiaeduca.infrastructure.repository;

import com.projeto.familiaeduca.domain.models.Justificativa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

/* Parte que interage com o banco de dados para informações sobre Justificativa */
@Repository
public interface JustificativaRepository extends JpaRepository<Justificativa, UUID> {
    List<Justificativa> findByResponsavelId(UUID idResponsavel);
}