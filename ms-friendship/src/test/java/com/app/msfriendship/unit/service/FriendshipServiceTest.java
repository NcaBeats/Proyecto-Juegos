package com.app.msfriendship.unit.service;

import com.app.msfriendship.client.UserClient;
import com.app.msfriendship.dto.FriendshipRequest;
import com.app.msfriendship.mapper.FriendshipMapper;
import com.app.msfriendship.model.Friendship;
import com.app.msfriendship.model.FriendshipStatus;
import com.app.msfriendship.repository.FriendshipRepository;
import com.app.msfriendship.service.FriendshipService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.app.msfriendship.support.FriendshipFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FriendshipServiceTest {

    @Mock
    FriendshipRepository friendshipRepository;
    @Mock
    FriendshipMapper friendshipMapper;
    @Mock
    UserClient userClient;

    @InjectMocks
    FriendshipService friendshipService;

    @Test
    void getFriends_ReturnsConcatenatedLists() {
        Friendship sent = createFriendshipEntity();
        sent.setStatus(FriendshipStatus.ACCEPTED);
        Friendship received = createFriendshipEntityFaker();
        received.setStatus(FriendshipStatus.ACCEPTED);

        when(friendshipRepository.findByUserIdAndStatus(USER_ID, FriendshipStatus.ACCEPTED)).thenReturn(List.of(sent));
        when(friendshipRepository.findByFriendIdAndStatus(USER_ID, FriendshipStatus.ACCEPTED)).thenReturn(List.of(received));
        when(friendshipMapper.toResponse(sent)).thenReturn(FRIENDSHIP_RESPONSE);
        when(friendshipMapper.toResponse(received)).thenReturn(FRIENDSHIP_RESPONSE);

        var result = friendshipService.getFriends(USER_ID);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(friendshipRepository).findByUserIdAndStatus(USER_ID, FriendshipStatus.ACCEPTED);
        verify(friendshipRepository).findByFriendIdAndStatus(USER_ID, FriendshipStatus.ACCEPTED);
    }

    @Test
    void getPendingRequests_ReturnsPending() {
        Friendship pending = createFriendshipEntity();

        when(friendshipRepository.findByFriendIdAndStatus(USER_ID, FriendshipStatus.PENDING)).thenReturn(List.of(pending));
        when(friendshipMapper.toResponse(pending)).thenReturn(FRIENDSHIP_RESPONSE);

        var result = friendshipService.getPendingRequests(USER_ID);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(friendshipRepository).findByFriendIdAndStatus(USER_ID, FriendshipStatus.PENDING);
    }

    @Test
    void sendRequest_Success() {
        Friendship saved = createFriendshipEntity();

        when(userClient.getUserById(USER_ID)).thenReturn(USER_RESPONSE);
        when(userClient.getUserById(FRIEND_ID)).thenReturn(USER_RESPONSE);
        when(friendshipRepository.existsByUserIdAndFriendId(USER_ID, FRIEND_ID)).thenReturn(false);
        when(friendshipRepository.existsByUserIdAndFriendId(FRIEND_ID, USER_ID)).thenReturn(false);
        when(friendshipRepository.save(any(Friendship.class))).thenReturn(saved);
        when(friendshipMapper.toResponse(saved)).thenReturn(FRIENDSHIP_RESPONSE);

        var result = friendshipService.sendRequest(USER_ID, FRIENDSHIP_REQUEST);

        assertNotNull(result);
        assertEquals(FRIENDSHIP_RESPONSE, result);
        verify(userClient).getUserById(USER_ID);
        verify(userClient).getUserById(FRIEND_ID);
        verify(friendshipRepository).save(any(Friendship.class));
    }

    @Test
    void sendRequest_WhenSelfRequest_ThrowsIllegalArgumentException() {
        FriendshipRequest selfRequest = FriendshipRequest.builder().friendId(USER_ID).build();

        assertThrows(IllegalArgumentException.class, () -> friendshipService.sendRequest(USER_ID, selfRequest));

        verifyNoInteractions(userClient, friendshipRepository, friendshipMapper);
    }

    @Test
    void sendRequest_WhenAlreadyExists_ThrowsIllegalStateException() {
        when(userClient.getUserById(USER_ID)).thenReturn(USER_RESPONSE);
        when(userClient.getUserById(FRIEND_ID)).thenReturn(USER_RESPONSE);
        when(friendshipRepository.existsByUserIdAndFriendId(USER_ID, FRIEND_ID)).thenReturn(true);

        assertThrows(IllegalStateException.class, () -> friendshipService.sendRequest(USER_ID, FRIENDSHIP_REQUEST));

        verify(friendshipRepository, never()).save(any());
    }

    @Test
    void acceptRequest_Success() {
        Friendship friendship = createFriendshipEntity();

        when(friendshipRepository.findById(ID)).thenReturn(Optional.of(friendship));
        when(friendshipRepository.save(friendship)).thenReturn(friendship);
        when(friendshipMapper.toResponse(friendship)).thenReturn(FRIENDSHIP_RESPONSE);

        var result = friendshipService.acceptRequest(FRIEND_ID, ID);

        assertNotNull(result);
        assertEquals(FriendshipStatus.ACCEPTED, friendship.getStatus());
        verify(friendshipRepository).findById(ID);
        verify(friendshipRepository).save(friendship);
    }

    @Test
    void acceptRequest_WhenNotFound_ThrowsEntityNotFoundException() {
        when(friendshipRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> friendshipService.acceptRequest(FRIEND_ID, ID));

        verify(friendshipRepository).findById(ID);
        verifyNoMoreInteractions(friendshipRepository);
    }

    @Test
    void acceptRequest_WhenNotFriend_ThrowsIllegalArgumentException() {
        Friendship friendship = createFriendshipEntity();

        when(friendshipRepository.findById(ID)).thenReturn(Optional.of(friendship));

        assertThrows(IllegalArgumentException.class, () -> friendshipService.acceptRequest(999L, ID));

        verify(friendshipRepository).findById(ID);
        verify(friendshipRepository, never()).save(any());
    }

    @Test
    void acceptRequest_WhenNotPending_ThrowsIllegalStateException() {
        Friendship friendship = createFriendshipEntity();
        friendship.setStatus(FriendshipStatus.ACCEPTED);

        when(friendshipRepository.findById(ID)).thenReturn(Optional.of(friendship));

        assertThrows(IllegalStateException.class, () -> friendshipService.acceptRequest(FRIEND_ID, ID));

        verify(friendshipRepository).findById(ID);
        verify(friendshipRepository, never()).save(any());
    }

    @Test
    void rejectRequest_Success() {
        Friendship friendship = createFriendshipEntity();

        when(friendshipRepository.findById(ID)).thenReturn(Optional.of(friendship));
        when(friendshipRepository.save(friendship)).thenReturn(friendship);
        when(friendshipMapper.toResponse(friendship)).thenReturn(FRIENDSHIP_RESPONSE);

        var result = friendshipService.rejectRequest(FRIEND_ID, ID);

        assertNotNull(result);
        assertEquals(FriendshipStatus.REJECTED, friendship.getStatus());
        verify(friendshipRepository).findById(ID);
        verify(friendshipRepository).save(friendship);
    }

    @Test
    void rejectRequest_WhenNotFound_ThrowsEntityNotFoundException() {
        when(friendshipRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> friendshipService.rejectRequest(FRIEND_ID, ID));

        verify(friendshipRepository).findById(ID);
        verifyNoMoreInteractions(friendshipRepository);
    }

    @Test
    void rejectRequest_WhenNotFriend_ThrowsIllegalArgumentException() {
        Friendship friendship = createFriendshipEntity();

        when(friendshipRepository.findById(ID)).thenReturn(Optional.of(friendship));

        assertThrows(IllegalArgumentException.class, () -> friendshipService.rejectRequest(999L, ID));

        verify(friendshipRepository).findById(ID);
        verify(friendshipRepository, never()).save(any());
    }

    @Test
    void rejectRequest_WhenNotPending_ThrowsIllegalStateException() {
        Friendship friendship = createFriendshipEntity();
        friendship.setStatus(FriendshipStatus.REJECTED);

        when(friendshipRepository.findById(ID)).thenReturn(Optional.of(friendship));

        assertThrows(IllegalStateException.class, () -> friendshipService.rejectRequest(FRIEND_ID, ID));

        verify(friendshipRepository).findById(ID);
        verify(friendshipRepository, never()).save(any());
    }

    @Test
    void deleteFriendship_WhenNotFound_ThrowsEntityNotFoundException() {
        when(friendshipRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> friendshipService.deleteFriendship(USER_ID, ID));

        verify(friendshipRepository).findById(ID);
        verify(friendshipRepository, never()).delete(any());
    }

    @Test
    void deleteFriendship_ThrowsIllegalArgumentException() {
        Friendship friendship = createFriendshipEntity();

        when(friendshipRepository.findById(ID)).thenReturn(Optional.of(friendship));

        assertThrows(IllegalArgumentException.class, () -> friendshipService.deleteFriendship(USER_ID, ID));

        verify(friendshipRepository).findById(ID);
        verify(friendshipRepository, never()).delete(any());
    }
}
