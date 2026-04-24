package com.app.msusuario.repository;

import com.app.msusuario.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("SELECT e FROM Usuario e WHERE " +
            "(:nombre IS NULL OR e.nombre = :nombre) AND " +
            "(:email IS NULL OR e.email = :email)")
    Page<Usuario> findByFiltros(@Param("nombre") String nombre,
                                @Param("email") String email,
                                Pageable pageable);

}
