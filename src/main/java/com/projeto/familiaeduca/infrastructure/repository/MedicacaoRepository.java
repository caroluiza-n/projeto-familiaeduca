package com.projeto.familiaeduca.infrastructure.repository;

import com.projeto.familiaeduca.domain.models.Medicacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface MedicacaoRepository extends JpaRepository<Medicacao, UUID> {
    List<Medicacao> findByAluno_MatriculaOrderByDataAplicacaoDesc(int matricula);
}
