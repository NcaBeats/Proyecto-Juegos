package com.example.msreview.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Entity
@Table(name = "review")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false,unique = true)
    private Long userId;

    @Column(nullable = false)
    private Long juegoId;

    @Column(nullable = false,length = 500)
    private String comentario;

    @Column(nullable = false)
    private Rating rating;

    @Column(nullable = false)
    private Instant fechaCreacion;
}
