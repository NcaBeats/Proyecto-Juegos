package com.app.msfriendship.controller;

import com.app.msfriendship.dto.FriendshipRequest;
import com.app.msfriendship.dto.FriendshipResponse;
import com.app.msfriendship.service.FriendshipService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/friendships")
public class FriendshipController {
    private final FriendshipService friendshipService;

    @PostMapping("/request")
    public ResponseEntity<FriendshipResponse> sendRequest(
            @RequestParam Long userId,
            @Valid @RequestBody FriendshipRequest request) {
        log.info("POST /api/v1/friendships/request - userId={} friendId={}", userId, request.friendId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(friendshipService.sendRequest(userId, request));
    }

    @PutMapping("/{id}/accept")
    public ResponseEntity<FriendshipResponse> acceptRequest(
            @PathVariable Long id,
            @RequestParam Long userId) {
        log.info("PUT /api/v1/friendships/{}/accept - userId={} friendshipId={}", id, userId, id);
        return ResponseEntity.ok(friendshipService.acceptRequest(userId, id));
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<FriendshipResponse> rejectRequest(
            @PathVariable Long id,
            @RequestParam Long userId) {
        log.info("PUT /api/v1/friendships/{}/reject - userId={} friendshipId={}", id, userId, id);
        return ResponseEntity.ok(friendshipService.rejectRequest(userId, id));
    }

    @GetMapping("/{userId}/friends")
    public ResponseEntity<List<FriendshipResponse>> getFriends(@PathVariable Long userId) {
        log.debug("GET /api/v1/friendships/{}/friends - userId={}", userId, userId);
        return ResponseEntity.ok(friendshipService.getFriends(userId));
    }

    @GetMapping("/{userId}/pending")
    public ResponseEntity<List<FriendshipResponse>> getPendingRequests(@PathVariable Long userId) {
        log.debug("GET /api/v1/friendships/{}/pending - userId={}", userId, userId);
        return ResponseEntity.ok(friendshipService.getPendingRequests(userId));
    }
    @DeleteMapping("/{friendShipId}/delete")
    public ResponseEntity<Void> deleteFriendship(@PathVariable Long friendShipId, @RequestParam Long userId) {
        log.info("DELETE /api/v1/friendships/{}/delete - userId={} friendshipId={}", friendShipId, userId, friendShipId);
        friendshipService.deleteFriendship(userId,friendShipId);
        return ResponseEntity.noContent().build();
    }
}
