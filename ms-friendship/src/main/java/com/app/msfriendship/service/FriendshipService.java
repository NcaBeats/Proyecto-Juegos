package com.app.msfriendship.service;

import com.app.msfriendship.client.UserClient;
import com.app.msfriendship.dto.FriendshipRequest;
import com.app.msfriendship.dto.FriendshipResponse;
import com.app.msfriendship.mapper.FriendshipMapper;
import com.app.msfriendship.model.Friendship;
import com.app.msfriendship.model.FriendshipStatus;
import com.app.msfriendship.repository.FriendshipRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FriendshipService {
    private final FriendshipRepository friendshipRepository;
    private final FriendshipMapper friendshipMapper;
    private final UserClient userClient;

    public List<FriendshipResponse> getFriends(Long userId) {
        var amistadesEnviadas = friendshipRepository.findByUserIdAndStatus(userId, FriendshipStatus.ACCEPTED);
        var amistadesRecibidas = friendshipRepository.findByFriendIdAndStatus(userId, FriendshipStatus.ACCEPTED);

        return Stream.concat(amistadesEnviadas.stream(), amistadesRecibidas.stream())
                .map(friendshipMapper::toResponse)
                .toList();
    }

    public List<FriendshipResponse> getPendingRequests(Long userId) {
        return friendshipRepository.findByFriendIdAndStatus(userId, FriendshipStatus.PENDING)
                .stream()
                .map(friendshipMapper::toResponse)
                .toList();
    }

    @Transactional
    public FriendshipResponse sendRequest(Long userId, FriendshipRequest request) {
        if (userId.equals(request.friendId())) {
            throw new IllegalArgumentException("No puedes enviarte solicitud a ti mismo");
        }

        userClient.getUserById(userId);
        userClient.getUserById(request.friendId());

        if (friendshipRepository.existsByUserIdAndFriendId(userId, request.friendId()) ||
            friendshipRepository.existsByUserIdAndFriendId(request.friendId(), userId)) {
            throw new IllegalStateException("Ya existe una relación de amistad");
        }

        Friendship friendship = Friendship.builder()
                .userId(userId)
                .friendId(request.friendId())
                .status(FriendshipStatus.PENDING)
                .createdAt(Instant.now())
                .build();

        return friendshipMapper.toResponse(friendshipRepository.save(friendship));
    }

    @Transactional
    public FriendshipResponse acceptRequest(Long userId, Long friendshipId) {
        Friendship friendship = friendshipRepository.findById(friendshipId)
                .orElseThrow(() -> new EntityNotFoundException("Solicitud no encontrada"));

        if (!friendship.getFriendId().equals(userId)) {
            throw new IllegalArgumentException("No puedes aceptar esta solicitud");
        }
        if (friendship.getStatus() != FriendshipStatus.PENDING) {
            throw new IllegalStateException("La solicitud ya no está pendiente");
        }

        friendship.setStatus(FriendshipStatus.ACCEPTED);
        return friendshipMapper.toResponse(friendshipRepository.save(friendship));
    }

    @Transactional
    public FriendshipResponse rejectRequest(Long userId, Long friendshipId) {
        Friendship friendship = friendshipRepository.findById(friendshipId)
                .orElseThrow(() -> new EntityNotFoundException("Solicitud no encontrada"));

        if (!friendship.getFriendId().equals(userId)) {
            throw new IllegalArgumentException("No puedes rechazar esta solicitud");
        }
        if (friendship.getStatus() != FriendshipStatus.PENDING) {
            throw new IllegalStateException("La solicitud ya no está pendiente");
        }

        friendship.setStatus(FriendshipStatus.REJECTED);
        return friendshipMapper.toResponse(friendshipRepository.save(friendship));
    }
}