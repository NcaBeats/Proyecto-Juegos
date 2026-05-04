package com.app.mswishlist.wishlistgame.model;

import com.app.mswishlist.wishlistgame.dto.WishlistGameRequest;
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
    @Column(name = "user_id", nullable = false)
    private Long userId;
    @Column(name = "game_id", nullable = false)
    private Long gameId;
    @CreatedDate
    private Instant fechaCreacion;
}
