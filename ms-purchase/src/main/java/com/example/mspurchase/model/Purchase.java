package com.example.mspurchase.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "compra")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)

public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;
    @Column(nullable = false)
    private Long juegoId;
    @Column(nullable = false)
    private Long usuarioId;
    @Column(nullable = false)
    private BigDecimal precio;
    @CreatedDate
    @Setter(AccessLevel.NONE)
    @Column(nullable = false)
    private Instant fechaRegistro;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoCompra estado;
}
