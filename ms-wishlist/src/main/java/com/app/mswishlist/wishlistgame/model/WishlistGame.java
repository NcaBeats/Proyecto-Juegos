package com.app.mswishlist.wishlistgame.model;

import com.app.mswishlist.wishlist.model.Wishlist;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.Instant;

@Entity
@Table(name = "wishlist_game",uniqueConstraints = {@UniqueConstraint(columnNames = {"wishlist_user_id", "game_id"})})
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

    @ManyToOne
    @JoinColumn(name = "wishlist_user_id",nullable = false)
    @JsonBackReference() // en la tabla hija para no serializar y evitar bucle en json
    private Wishlist wishlist;

    @CreatedDate
    private Instant fechaCreacion;
}
