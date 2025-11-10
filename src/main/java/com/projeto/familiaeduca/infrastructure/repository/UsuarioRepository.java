package com.projeto.familiaeduca.infrastructure.repository;

import com.projeto.familiaeduca.domain.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

/* Parte que interage com o banco de dados para informações sobre Usuario */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    boolean existsByEmail(String email);
    Optional<Usuario> findByEmail(String email);
}
