package com.example.msreview.mapper;

import com.example.msreview.dto.ReviewRequest;
import com.example.msreview.dto.ReviewResponse;
import com.example.msreview.dto.external.JuegoResponse;
import com.example.msreview.dto.external.ProfileResponse;
import com.example.msreview.model.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {
    public Review toEntity (ReviewRequest request) {
        return Review.builder()
                .userId(request.userId())
                .juegoId(request.juegoId())
                .comentario(request.comentario())
                .rating(request.rating())
                .build();

    }
    public ReviewResponse toResponse (Review review, JuegoResponse juego, ProfileResponse profile) {
        return ReviewResponse.builder()
                .id(review.getId())
                .userId(review.getUserId())
                .nickname(profile.nickname())
                .avatar(profile.avatar())
                .juegoId(review.getJuegoId())
                .nombreJuego(juego.nombre())
                .comentario(review.getComentario())
                .rating(review.getRating().getValue())
                .fechaCreacion(review.getFechaCreacion())
                .build();
    }
}
