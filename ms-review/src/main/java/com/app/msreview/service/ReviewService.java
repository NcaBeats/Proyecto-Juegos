package com.app.msreview.service;

import com.app.msreview.client.JuegoClient;
import com.app.msreview.client.NotificationClient;
import com.app.msreview.client.ProfileClient;
import com.app.msreview.dto.ReviewRequest;
import com.app.msreview.dto.ReviewResponse;
import com.app.msreview.dto.external.JuegoResponse;
import com.app.msreview.dto.external.NotificationRequest;
import com.app.msreview.dto.external.ProfileResponse;
import com.app.msreview.mapper.ReviewMapper;
import com.app.msreview.model.Review;
import com.app.msreview.dto.external.enums.TipoNotification;
import com.app.msreview.repository.ReviewRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final JuegoClient juegoClient;
    private final ProfileClient profileClient;
    private final NotificationClient notificationClient;

    public List<ReviewResponse> findAllByJuegoId(Long id) {
        log.debug("Obteniendo reviews para juegoId={}", id);
        var reviews = reviewRepository.findAllByJuegoId(id)
                .stream()
                .map(review -> {
                    log.debug("Llamando a JuegoClient.findById gameId={}", review.getJuegoId());
                    JuegoResponse juegoResponse = juegoClient.findById(review.getJuegoId());
                    log.debug("Llamando a ProfileClient.findByUserId userId={}", review.getUserId());
                    ProfileResponse profileResponse = profileClient.findByUserId(review.getUserId());
                    return reviewMapper.toResponse(review, juegoResponse, profileResponse);
                })
                .toList();
        log.debug("Encontradas {} reviews para juegoId={}", reviews.size(), id);
        return reviews;
    }

    public List<ReviewResponse> findAllByUserId(Long id) {
        log.debug("Obteniendo reviews para userId={}", id);
        var reviews = reviewRepository.findAllByUserId(id)
                .stream()
                .map(review -> {
                    log.debug("Llamando a JuegoClient.findById gameId={}", review.getJuegoId());
                    JuegoResponse juegoResponse = juegoClient.findById(review.getJuegoId());
                    log.debug("Llamando a ProfileClient.findByUserId userId={}", review.getUserId());
                    ProfileResponse profileResponse = profileClient.findByUserId(review.getUserId());
                    return reviewMapper.toResponse(review, juegoResponse, profileResponse);
                }).toList();
        log.debug("Encontradas {} reviews para userId={}", reviews.size(), id);
        return reviews;
    }

    @Transactional
    public ReviewResponse save (ReviewRequest reviewRequest) {
        log.info("Creando review userId={} juegoId={}", reviewRequest.userId(), reviewRequest.juegoId());
        log.debug("Llamando a JuegoClient.findById gameId={}", reviewRequest.juegoId());
        JuegoResponse juegoResponse = juegoClient.findById(reviewRequest.juegoId());
        log.debug("Llamando a ProfileClient.findByUserId userId={}", reviewRequest.userId());
        ProfileResponse profileResponse = profileClient.findByUserId(reviewRequest.userId());
        Review review = reviewMapper.toEntity(reviewRequest);
        Review savedReview = reviewRepository.save(review);

        NotificationRequest notificationRequest = NotificationRequest.builder()
                .userId(reviewRequest.userId())
                .message("Has publicado una review para el juego: " + juegoResponse.nombre())
                .tipo(TipoNotification.REVIEW)
                .gameId(reviewRequest.juegoId())
                .build();
        try{
            log.debug("Llamando a NotificationClient.createNotification userId={}", reviewRequest.userId());
            notificationClient.createNotification(notificationRequest);
            log.info("Notificación de review enviada userId={} juegoId={}", reviewRequest.userId(), reviewRequest.juegoId());
        }
        catch (Exception e){
            log.warn("Error al enviar la notificación de review userId={} juegoId={}: {}", reviewRequest.userId(), reviewRequest.juegoId(), e.getMessage());
        }

        return reviewMapper.toResponse(savedReview, juegoResponse, profileResponse);
    }
    @Transactional
    public ReviewResponse update(Long id ,ReviewRequest reviewRequest) {
        log.info("Actualizando review id={} userId={}", id, reviewRequest.userId());
        Review review = reviewRepository.findById(id).orElseThrow( ()-> new EntityNotFoundException("Review no encontrada con el id: " + id) );
        review.update(reviewRequest);
        JuegoResponse juegoResponse = juegoClient.findById(review.getJuegoId());
        ProfileResponse profileResponse = profileClient.findByUserId(review.getUserId());
        log.info("Review actualizada id={} userId={}", id, reviewRequest.userId());
        return reviewMapper.toResponse(review, juegoResponse, profileResponse);
    }
    @Transactional
    public void delete(Long id) {
        log.info("Eliminando review id={}", id);
        if (!reviewRepository.existsById(id)){
            throw new EntityNotFoundException("Review no encontrada con el id: " + id);
        }
        reviewRepository.deleteById(id);
        log.info("Review eliminada id={}", id);
    }
}
