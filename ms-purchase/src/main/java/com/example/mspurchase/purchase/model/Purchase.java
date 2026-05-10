package com.example.mspurchase.purchase.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "purchase")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false)
    private BigDecimal totalPrecio;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant fechaCompra;
}