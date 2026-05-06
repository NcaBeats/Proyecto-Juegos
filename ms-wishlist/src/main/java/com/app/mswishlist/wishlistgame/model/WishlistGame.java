package com.app.mswishlist.wishlistgame.model;

import com.app.mswishlist.wishlist.model.Wishlist;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.Instant;

@Entity
@Table(name = "wishlist_game",uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "game_id"})})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class WishlistGame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(name = "game_id", nullable = false)
    private Long gameId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)// en la tabla hija para no serializar y evitar bucle en json
    private Wishlist wishlist;

    @CreatedDate
    @Column(nullable = false)
    @Setter(AccessLevel.NONE)
    private Instant fechaCreacion;
}
