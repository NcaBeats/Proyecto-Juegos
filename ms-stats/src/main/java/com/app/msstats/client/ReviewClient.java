package com.app.msstats.client;

import com.app.msstats.dto.external.ReviewResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@FeignClient(name = "ms-review")
public interface ReviewClient {

    @GetMapping("/api/v1/reviews/game/{gameId}")
    List<ReviewResponse> getReviewsByGameId(@PathVariable Long gameId);

    @GetMapping("/api/v1/reviews/user/{userId}")
    List<ReviewResponse> getReviewsByUserId(@PathVariable Long userId);
}
