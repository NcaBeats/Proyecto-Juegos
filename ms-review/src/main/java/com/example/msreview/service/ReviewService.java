package com.example.msreview.service;

import com.example.msreview.client.JuegoClient;
import com.example.msreview.client.NotificationClient;
import com.example.msreview.client.ProfileClient;
import com.example.msreview.dto.ReviewRequest;
import com.example.msreview.dto.ReviewResponse;
import com.example.msreview.dto.external.JuegoResponse;
import com.example.msreview.dto.external.NotificationRequest;
import com.example.msreview.dto.external.ProfileResponse;
import com.example.msreview.mapper.ReviewMapper;
import com.example.msreview.model.Review;
import com.example.msreview.model.TipoNotification;
import com.example.msreview.repository.ReviewRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final JuegoClient juegoClient;
    private final ProfileClient profileClient;
    private final NotificationClient notificationClient;

    public List<ReviewResponse> findAllByJuegoId(Long id) {
        return reviewRepository.findAllByJuegoId(id)
                .stream()
                .map(review -> {
                    JuegoResponse juegoResponse = juegoClient.findById(review.getJuegoId());
                    ProfileResponse profileResponse = profileClient.findByUserId(review.getUserId());
                    return reviewMapper.toResponse(review, juegoResponse, profileResponse);
                })
                .toList();
    }

    public List<ReviewResponse> findAllByUserId(Long id) {
        return reviewRepository.findAllByUserId(id)
                .stream()
                .map(review -> {
                    JuegoResponse juegoResponse = juegoClient.findById(review.getJuegoId());
                    ProfileResponse profileResponse = profileClient.findByUserId(review.getUserId());
                    return reviewMapper.toResponse(review, juegoResponse, profileResponse);
                }).toList();
    }

    @Transactional
    public ReviewResponse save (ReviewRequest reviewRequest) {
        JuegoResponse juegoResponse = juegoClient.findById(reviewRequest.juegoId());
        ProfileResponse profileResponse = profileClient.findByUserId(reviewRequest.userId());
        Review review = reviewMapper.toEntity(reviewRequest);
        Review savedReview = reviewRepository.save(review);

        NotificationRequest notificationRequest = NotificationRequest.builder()
                .userId(reviewRequest.userId())
                .message("Has publicado una review para el juego: " + juegoResponse.nombre())
                .tipo(TipoNotification.REVIEW)
                .gameId(reviewRequest.juegoId())
                .build();
        notificationClient.createNotification(notificationRequest);

        return reviewMapper.toResponse(savedReview, juegoResponse, profileResponse);
    }
    @Transactional
    public ReviewResponse update(Long id ,ReviewRequest reviewRequest) {
        Review review = reviewRepository.findById(id).orElseThrow( ()-> new EntityNotFoundException("Review no encontrada con el id: " + id) );
        review.update(reviewRequest);
        JuegoResponse juegoResponse = juegoClient.findById(review.getJuegoId());
        ProfileResponse profileResponse = profileClient.findByUserId(review.getUserId());
        Review savedReview = reviewRepository.save(review);
        return reviewMapper.toResponse(savedReview, juegoResponse, profileResponse);
    }
    @Transactional
    public void delete(Long id) {
        if (!reviewRepository.existsById(id)){
            throw new EntityNotFoundException("Review no encontrada con el id: " + id);
        }
        reviewRepository.deleteById(id);
    }
}
