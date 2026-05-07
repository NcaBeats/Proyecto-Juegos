package com.app.msjuego.estudio.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "estudio")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Estudio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false)
    private String nombre;

}
