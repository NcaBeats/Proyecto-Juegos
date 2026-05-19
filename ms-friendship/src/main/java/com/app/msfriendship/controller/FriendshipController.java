package com.app.msfriendship.controller;

import com.app.msfriendship.dto.FriendshipRequest;
import com.app.msfriendship.dto.FriendshipResponse;
import com.app.msfriendship.service.FriendshipService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/friendships")
public class FriendshipController {
    private final FriendshipService friendshipService;

    @PostMapping("/request")
    public ResponseEntity<FriendshipResponse> sendRequest(
            @RequestParam Long userId,
            @Valid @RequestBody FriendshipRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(friendshipService.sendRequest(userId, request));
    }

    @PutMapping("/{id}/accept")
    public ResponseEntity<FriendshipResponse> acceptRequest(
            @PathVariable Long id,
            @RequestParam Long userId) {
        return ResponseEntity.ok(friendshipService.acceptRequest(userId, id));
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<FriendshipResponse> rejectRequest(
            @PathVariable Long id,
            @RequestParam Long userId) {
        return ResponseEntity.ok(friendshipService.rejectRequest(userId, id));
    }

    @GetMapping("/{userId}/friends")
    public ResponseEntity<List<FriendshipResponse>> getFriends(@PathVariable Long userId) {
        return ResponseEntity.ok(friendshipService.getFriends(userId));
    }

    @GetMapping("/{userId}/pending")
    public ResponseEntity<List<FriendshipResponse>> getPendingRequests(@PathVariable Long userId) {
        return ResponseEntity.ok(friendshipService.getPendingRequests(userId));
    }
    @DeleteMapping("/{friendShipId}/delete")
    public ResponseEntity<Void> deleteFriendship(@PathVariable Long friendShipId, @RequestParam Long userId) {
        friendshipService.deleteFriendship(userId,friendShipId);
        return ResponseEntity.noContent().build();
    }
}