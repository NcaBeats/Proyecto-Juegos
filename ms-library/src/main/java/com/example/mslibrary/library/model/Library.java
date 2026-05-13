package com.example.mslibrary.library.model;

import com.example.mslibrary.librarygame.model.LibraryGame;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "library")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@Builder
public class Library {

    @Id
    @Column(nullable = false)
    @Setter(AccessLevel.NONE)
    private Long userId;

    @OneToMany(mappedBy = "library", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<LibraryGame> games = new HashSet<>();

    @CreatedDate
    @Column(nullable = false)
    private Instant fechaCreacion;
}
