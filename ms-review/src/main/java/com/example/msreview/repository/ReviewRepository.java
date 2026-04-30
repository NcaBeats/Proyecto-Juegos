package com.example.msreview.repository;

import com.example.msreview.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    Optional<Review> findByUserId(Long id);
}
