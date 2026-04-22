package com.app.msjuego.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "juego")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Juego {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;
    @Column(length = 50, nullable = false,unique = true)
    private String nombre;
}
