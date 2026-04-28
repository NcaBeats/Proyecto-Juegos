package com.app.msprofile.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.Instant;

@Entity
@Table(name = "profiles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Profile {

    @Id
    @Setter(AccessLevel.NONE)
    private Long userId;

    @Column(unique = true, nullable = false,length = 30)
    private String nickname;

    @Column(nullable = true)
    private String avatar;

    @Column(nullable = true,length = 150)
    private String bio;

    @Column(nullable = false)
    private TipoPerfil tipoPerfil;

    @CreatedDate
    @Column(nullable = false)
    private Instant fechaRegistro;
}
