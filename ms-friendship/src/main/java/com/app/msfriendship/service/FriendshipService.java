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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class FriendshipService {
    private final FriendshipRepository friendshipRepository;
    private final FriendshipMapper friendshipMapper;
    private final UserClient userClient;

    public List<FriendshipResponse> getFriends(Long userId) {
        log.debug("Obteniendo amistades aceptadas para userId={}", userId);
        var amistadesEnviadas = friendshipRepository.findByUserIdAndStatus(userId, FriendshipStatus.ACCEPTED);
        var amistadesRecibidas = friendshipRepository.findByFriendIdAndStatus(userId, FriendshipStatus.ACCEPTED);
        log.debug("Amistades enviadas: {} - Amistades recibidas: {}", amistadesEnviadas.size(), amistadesRecibidas.size());

        return Stream.concat(amistadesEnviadas.stream(), amistadesRecibidas.stream())
                .map(friendshipMapper::toResponse)
                .toList();
    }

    public List<FriendshipResponse> getPendingRequests(Long userId) {
        log.debug("Obteniendo solicitudes pendientes para userId={}", userId);
        var pending = friendshipRepository.findByFriendIdAndStatus(userId, FriendshipStatus.PENDING);
        log.debug("Solicitudes pendientes encontradas: {}", pending.size());
        return pending.stream().map(friendshipMapper::toResponse).toList();
    }

    @Transactional
    public FriendshipResponse sendRequest(Long userId, FriendshipRequest request) {
        log.info("Enviando solicitud de amistad - userId={} friendId={}", userId, request.friendId());
        if (userId.equals(request.friendId())) {
            throw new IllegalArgumentException("No puedes enviarte solicitud a ti mismo");
        }

        log.debug("Llamando a UserClient.getUserById userId={}", userId);
        userClient.getUserById(userId);
        log.debug("Llamando a UserClient.getUserById userId={}", request.friendId());
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

        Friendship saved = friendshipRepository.save(friendship);
        log.info("Solicitud de amistad creada con id={} userId={} friendId={}", saved.getId(), saved.getUserId(), saved.getFriendId());
        return friendshipMapper.toResponse(saved);
    }

    @Transactional
    public FriendshipResponse acceptRequest(Long userId, Long friendshipId) {
        log.info("Aceptando solicitud de amistad - userId={} friendshipId={}", userId, friendshipId);
        Friendship friendship = friendshipRepository.findById(friendshipId)
                .orElseThrow(() -> new EntityNotFoundException("Solicitud no encontrada"));

        if (!friendship.getFriendId().equals(userId)) {
            throw new IllegalArgumentException("No puedes aceptar esta solicitud");
        }
        if (!friendship.getStatus().equals(FriendshipStatus.PENDING) ) {
            throw new IllegalStateException("La solicitud ya no está pendiente");
        }

        friendship.setStatus(FriendshipStatus.ACCEPTED);
        Friendship saved = friendshipRepository.save(friendship);
        log.info("Solicitud aceptada - id={} userId={} friendId={}", saved.getId(), saved.getUserId(), saved.getFriendId());
        return friendshipMapper.toResponse(saved);
    }

    @Transactional
    public FriendshipResponse rejectRequest(Long userId, Long friendshipId) {
        log.info("Rechazando solicitud de amistad - userId={} friendshipId={}", userId, friendshipId);
        Friendship friendship = friendshipRepository.findById(friendshipId)
                .orElseThrow(() -> new EntityNotFoundException("Solicitud no encontrada"));

        if (!friendship.getFriendId().equals(userId)) {
            throw new IllegalArgumentException("No puedes rechazar esta solicitud");
        }
        if (friendship.getStatus() != FriendshipStatus.PENDING) {
            throw new IllegalStateException("La solicitud ya no está pendiente");
        }

        friendship.setStatus(FriendshipStatus.REJECTED);
        Friendship saved = friendshipRepository.save(friendship);
        log.info("Solicitud rechazada - id={} userId={} friendId={}", saved.getId(), saved.getUserId(), saved.getFriendId());
        return friendshipMapper.toResponse(saved);
    }
    @Transactional
    public void deleteFriendship(Long userId, Long friendshipId) {
        log.info("Eliminando relación de amistad - userId={} friendshipId={}", userId, friendshipId);
        Friendship friendship = friendshipRepository.findById(friendshipId)
                .orElseThrow(() -> new EntityNotFoundException("Relación de amistad no encontrada"));
        if (!friendship.getFriendId().equals(userId)||!friendship.getUserId().equals(userId)) {
            throw new IllegalArgumentException("No perteneces a esta relación de amistad");
        }
        friendshipRepository.delete(friendship);
        log.info("Relación de amistad eliminada - id={} userId={} friendId={}", friendshipId, friendship.getUserId(), friendship.getFriendId());
    }
}
