package com.example.msreview.repository;

import com.example.msreview.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    List<Review> findAllByJuegoId(Long id);
    List<Review> findAllByUserId(Long id);
}
