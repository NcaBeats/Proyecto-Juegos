package com.app.mswishlist.wishlist.model;

import com.app.mswishlist.wishlistgame.model.WishlistGame;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "wishlist")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@Builder
public class Wishlist {
    @Id
    @Column(nullable = false)
    @Setter(AccessLevel.NONE)
    private Long userId;

    @CreatedDate
    @Column(nullable = false)
    private Instant fechaCreacion;

    @OneToMany(mappedBy = "wishlist", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<WishlistGame> games = new HashSet<>();
}