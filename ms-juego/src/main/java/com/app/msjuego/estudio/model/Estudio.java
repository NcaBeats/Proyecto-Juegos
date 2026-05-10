package com.app.msjuego.estudio.model;

import com.app.msjuego.estudio.dto.EstudioRequest;
import com.app.msjuego.juego.model.Juego;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "estudio")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Estudio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @OneToMany(mappedBy = "estudio", fetch = FetchType.LAZY)
    private List<Juego> juegos = new ArrayList<>();

    @CreatedDate
    @Column(nullable = false)
    @Setter(AccessLevel.NONE)
    private Instant fechaCreacion;

    public void update(EstudioRequest request) {
        this.nombre = request.nombre();
    }
}
