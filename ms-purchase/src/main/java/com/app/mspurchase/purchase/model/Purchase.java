package com.app.mspurchase.purchase.model;

import com.app.mspurchase.purchasegame.model.PurchaseGame;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

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
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @OneToMany(mappedBy = "purchase",fetch = FetchType.LAZY,cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<PurchaseGame> juegos = new ArrayList<>();

    @Column(nullable = false)
    private BigDecimal totalPrecio;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private Instant fechaCompra;
}