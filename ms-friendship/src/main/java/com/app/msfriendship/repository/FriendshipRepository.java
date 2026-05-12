package com.app.msfriendship.repository;

import com.app.msfriendship.model.Friendship;
import com.app.msfriendship.model.FriendshipStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    Optional<Friendship> findByUserIdAndFriendId(Long userId, Long friendId);

    boolean existsByUserIdAndFriendId(Long userId, Long friendId);

    List<Friendship> findByUserIdAndStatus(Long userId, FriendshipStatus status);

    List<Friendship> findByFriendIdAndStatus(Long friendId, FriendshipStatus status);
}