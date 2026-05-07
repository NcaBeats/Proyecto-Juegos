package com.app.msjuego.juego.model;

import com.app.msjuego.estudio.model.Estudio;
import com.app.msjuego.genero.model.Genero;
import com.app.msjuego.juego.dto.JuegoRequest;
import com.app.msjuego.plataforma.model.Plataforma;
import jakarta.persistence.*;
import lombok.*;
import org.apache.logging.log4j.util.Lazy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "juego")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Juego {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;
    @Column(length = 50, nullable = false,unique = true)
    private String nombre;
    @Column(nullable = false)
    private String descripcion;
    @Column(nullable = false)
    private BigDecimal precio;
    @Column(nullable = false)
    private LocalDate fechaLanzamiento;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoJuego estado;

    @ManyToOne(fetch = FetchType.LAZY)
    private Estudio estudio;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "juego_genero",
            joinColumns = @JoinColumn(name = "juego_id"),
            inverseJoinColumns = @JoinColumn(name = "genero_id"))
    private List<Genero> generos = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "juego_plataforma",
            joinColumns = @JoinColumn(name = "juego_id"),
            inverseJoinColumns = @JoinColumn(name = "plataforma_id"))
    private List<Plataforma> plataformas = new ArrayList<>();

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant fecha_registro;

    public void update(JuegoRequest request) {
        this.nombre = request.nombre();
        this.descripcion = request.descripcion();
        this.precio = request.precio();
        this.fechaLanzamiento = request.fechaLanzamiento();
        this.estado = request.estado();
    }
}
