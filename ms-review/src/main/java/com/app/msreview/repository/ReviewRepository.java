package com.app.msreview.repository;

import com.app.msreview.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    List<Review> findAllByJuegoId(Long id);
    List<Review> findAllByUserId(Long id);
}
