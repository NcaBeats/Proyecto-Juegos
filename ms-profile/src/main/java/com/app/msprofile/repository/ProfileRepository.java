package com.app.msprofile.repository;

import com.app.msprofile.model.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface ProfileRepository extends JpaRepository<Profile,Long> {
    Optional<Profile> findByUserId(Long userId);
    Boolean existsByUserId(long userId);
    @Query("SELECT p FROM Profile p WHERE " +
            "(:userId IS NULL OR p.userId = :userId) AND " +
            "(:nickname IS NULL OR p.nickname = :nickname)")
    Page<Profile> findByFiltros(@Param("userId") Long userId,
                                @Param("nickname") String nickname,
                                Pageable pageable);

}
