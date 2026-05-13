package com.app.msstats.client;

import com.app.msstats.dto.external.ReviewResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@FeignClient(name = "review-service-stats", url = "http://localhost:8084/api/v1/reviews")
public interface ReviewClient {

    @GetMapping("/game/{gameId}")
    List<ReviewResponse> getReviewsByGameId(@PathVariable Long gameId);

    @GetMapping("/user/{userId}")
    List<ReviewResponse> getReviewsByUserId(@PathVariable Long userId);
}
