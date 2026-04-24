package com.app.msusuario.model;

import com.app.msusuario.dto.UsuarioRequest;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.Instant;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false,unique = true,length = 100)
    private String email;

    @CreatedDate
    @Column(nullable = false)
    private Instant fecha_registro;

    public void update(UsuarioRequest request){
        this.nombre = request.nombre();
        this.email = request.email();
    }
}
