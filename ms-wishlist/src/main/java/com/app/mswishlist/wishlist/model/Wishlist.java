package com.app.mswishlist.wishlist.model;

import com.app.mswishlist.wishlistgame.model.WishlistGame;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "wishlist")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Wishlist {
    @Id
    @Column(nullable = false)
    @Setter(AccessLevel.NONE)
    private Long userId;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "wishlist",cascade = CascadeType.ALL)
    @JsonManagedReference // en la tabla padre para si serializar
    private List<WishlistGame> games = new ArrayList<>();

    @CreatedDate
    @Column(nullable = false)
    private Instant fechaCreacion;
}