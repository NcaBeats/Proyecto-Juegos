package com.app.msjuego.plataforma.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "plataforma")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Plataforma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false)
    private String nombre;

}
