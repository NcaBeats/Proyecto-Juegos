package com.app.msjuego.model;

import com.app.msjuego.dto.JuegoRequest;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.math.BigDecimal;
import java.time.Instant;

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
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant fecha_registro;

    public void update(JuegoRequest request) {
        this.nombre = request.nombre();
        this.descripcion = request.descripcion();
        this.precio = request.precio();
    }
}
