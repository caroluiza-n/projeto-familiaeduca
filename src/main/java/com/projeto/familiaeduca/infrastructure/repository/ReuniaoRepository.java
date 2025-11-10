package com.projeto.familiaeduca.infrastructure.repository;

import com.projeto.familiaeduca.domain.models.Reuniao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

/* Parte que interage com o banco de dados para informações sobre Reuniao */
@Repository
public interface ReuniaoRepository extends JpaRepository<Reuniao, UUID> {
    List<Reuniao> findByResponsavelId(UUID idResponsavel);
}