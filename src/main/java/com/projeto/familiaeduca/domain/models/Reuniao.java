package com.projeto.familiaeduca.domain.models;

import com.projeto.familiaeduca.domain.models.enums.StatusRenovacao;
import com.projeto.familiaeduca.domain.models.enums.StatusReuniao;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Reuniao {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private LocalDate data;

    @Lob
    private String motivo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusReuniao status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_responsavel", nullable = false)
    private Responsavel responsavel;
}
