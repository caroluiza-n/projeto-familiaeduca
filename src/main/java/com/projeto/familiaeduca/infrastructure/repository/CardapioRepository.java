package com.projeto.familiaeduca.infrastructure.repository;

import com.projeto.familiaeduca.domain.models.Cardapio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

/* Parte que interage com o banco de dados para informações sobre Cardapio */
@Repository
public interface CardapioRepository extends JpaRepository<Cardapio, UUID> {
}