package com.app.msreview.unit.service;

import com.app.msreview.client.JuegoClient;
import com.app.msreview.client.NotificationClient;
import com.app.msreview.client.ProfileClient;
import com.app.msreview.dto.ReviewRequest;
import com.app.msreview.dto.external.NotificationRequest;
import com.app.msreview.mapper.ReviewMapper;
import com.app.msreview.model.Review;
import com.app.msreview.repository.ReviewRepository;
import com.app.msreview.service.ReviewService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.app.msreview.support.ReviewFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    @Mock
    ReviewRepository reviewRepository;
    @Mock
    ReviewMapper reviewMapper;
    @Mock
    JuegoClient juegoClient;
    @Mock
    ProfileClient profileClient;
    @Mock
    NotificationClient notificationClient;

    @InjectMocks
    ReviewService reviewService;

    @Test
    void findAllByJuegoId_ReturnsList() {
        Review review = createReviewEntity();
        List<Review> reviews = List.of(review);

        when(reviewRepository.findAllByJuegoId(JUEGO_ID)).thenReturn(reviews);
        when(juegoClient.findById(review.getJuegoId())).thenReturn(JUEGO_RESPONSE);
        when(profileClient.findByUserId(review.getUserId())).thenReturn(PROFILE_RESPONSE);
        when(reviewMapper.toResponse(review, JUEGO_RESPONSE, PROFILE_RESPONSE)).thenReturn(REVIEW_RESPONSE);

        var result = reviewService.findAllByJuegoId(JUEGO_ID);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(reviewRepository).findAllByJuegoId(JUEGO_ID);
        verify(juegoClient).findById(review.getJuegoId());
        verify(profileClient).findByUserId(review.getUserId());
    }

    @Test
    void findAllByUserId_ReturnsList() {
        Review review = createReviewEntity();
        List<Review> reviews = List.of(review);

        when(reviewRepository.findAllByUserId(USER_ID)).thenReturn(reviews);
        when(juegoClient.findById(review.getJuegoId())).thenReturn(JUEGO_RESPONSE);
        when(profileClient.findByUserId(review.getUserId())).thenReturn(PROFILE_RESPONSE);
        when(reviewMapper.toResponse(review, JUEGO_RESPONSE, PROFILE_RESPONSE)).thenReturn(REVIEW_RESPONSE);

        var result = reviewService.findAllByUserId(USER_ID);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(reviewRepository).findAllByUserId(USER_ID);
        verify(juegoClient).findById(review.getJuegoId());
        verify(profileClient).findByUserId(review.getUserId());
    }

    @Test
    void save_Success_WithNotification() {
        Review review = createReviewEntity();

        when(juegoClient.findById(REVIEW_REQUEST.juegoId())).thenReturn(JUEGO_RESPONSE);
        when(profileClient.findByUserId(REVIEW_REQUEST.userId())).thenReturn(PROFILE_RESPONSE);
        when(reviewMapper.toEntity(REVIEW_REQUEST)).thenReturn(review);
        when(reviewRepository.save(review)).thenReturn(review);
        when(reviewMapper.toResponse(review, JUEGO_RESPONSE, PROFILE_RESPONSE)).thenReturn(REVIEW_RESPONSE);

        var result = reviewService.save(REVIEW_REQUEST);

        assertNotNull(result);
        verify(juegoClient).findById(REVIEW_REQUEST.juegoId());
        verify(profileClient).findByUserId(REVIEW_REQUEST.userId());
        verify(reviewRepository).save(review);
        verify(notificationClient).createNotification(any(NotificationRequest.class));
    }

    @Test
    void save_Success_WhenNotificationFails() {
        Review review = createReviewEntity();

        when(juegoClient.findById(REVIEW_REQUEST.juegoId())).thenReturn(JUEGO_RESPONSE);
        when(profileClient.findByUserId(REVIEW_REQUEST.userId())).thenReturn(PROFILE_RESPONSE);
        when(reviewMapper.toEntity(REVIEW_REQUEST)).thenReturn(review);
        when(reviewRepository.save(review)).thenReturn(review);
        when(reviewMapper.toResponse(review, JUEGO_RESPONSE, PROFILE_RESPONSE)).thenReturn(REVIEW_RESPONSE);

        doThrow(new RuntimeException("Error de conexión")).when(notificationClient).createNotification(any(NotificationRequest.class));

        var result = reviewService.save(REVIEW_REQUEST);

        assertNotNull(result);
        verify(reviewRepository).save(review);
        verify(notificationClient).createNotification(any(NotificationRequest.class));
    }

    @Test
    void update_Success() {
        Review review = createReviewEntity();
        ReviewRequest requestFaker = createReviewRequestFaker();

        when(reviewRepository.findById(ID)).thenReturn(Optional.of(review));
        when(juegoClient.findById(review.getJuegoId())).thenReturn(JUEGO_RESPONSE);
        when(profileClient.findByUserId(review.getUserId())).thenReturn(PROFILE_RESPONSE);
        when(reviewMapper.toResponse(review, JUEGO_RESPONSE, PROFILE_RESPONSE)).thenReturn(REVIEW_RESPONSE);

        var result = reviewService.update(ID, requestFaker);

        assertNotNull(result);
        verify(reviewRepository).findById(ID);
        verify(juegoClient).findById(review.getJuegoId());
        verify(profileClient).findByUserId(review.getUserId());
    }

    @Test
    void update_ThrowsEntityNotFoundException() {
        ReviewRequest requestFaker = createReviewRequestFaker();
        when(reviewRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> reviewService.update(ID, requestFaker));

        verify(reviewRepository).findById(ID);
        verifyNoInteractions(juegoClient, profileClient, reviewMapper);
    }

    @Test
    void delete_Success() {
        when(reviewRepository.existsById(ID)).thenReturn(true);

        reviewService.delete(ID);

        verify(reviewRepository).existsById(ID);
        verify(reviewRepository).deleteById(ID);
    }

    @Test
    void delete_ThrowsEntityNotFoundException() {
        when(reviewRepository.existsById(ID)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> reviewService.delete(ID));

        verify(reviewRepository).existsById(ID);
        verify(reviewRepository, times(0)).deleteById(ID);
    }

}
