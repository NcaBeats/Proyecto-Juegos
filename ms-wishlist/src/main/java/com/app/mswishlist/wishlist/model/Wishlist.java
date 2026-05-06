package com.app.mswishlist.wishlist.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.Instant;

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
}