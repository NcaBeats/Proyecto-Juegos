package com.app.msfriendship.support;

import com.app.msfriendship.dto.FriendshipRequest;
import com.app.msfriendship.dto.FriendshipResponse;
import com.app.msfriendship.dto.external.UserResponse;
import com.app.msfriendship.model.Friendship;
import com.app.msfriendship.model.FriendshipStatus;
import net.datafaker.Faker;

import java.time.Instant;
import java.util.Locale;

public class FriendshipFactory {
    public static final Faker FAKER = new Faker(Locale.of("es"));

    public static final Long ID = 1L;
    public static final Long USER_ID = 1L;
    public static final Long FRIEND_ID = 2L;
    public static final FriendshipStatus STATUS = FriendshipStatus.PENDING;
    public static final Instant CREATED_AT = Instant.parse("2024-01-01T00:00:00Z");

    public static final UserResponse USER_RESPONSE = new UserResponse(USER_ID, "User", "user@test.com");

    public static final FriendshipRequest FRIENDSHIP_REQUEST = FriendshipRequest.builder()
            .friendId(FRIEND_ID)
            .build();

    public static final FriendshipResponse FRIENDSHIP_RESPONSE = FriendshipResponse.builder()
            .id(ID)
            .userId(USER_ID)
            .friendId(FRIEND_ID)
            .status(STATUS)
            .createdAt(CREATED_AT)
            .build();

    public static Friendship createFriendshipEntity() {
        return Friendship.builder()
                .id(ID)
                .userId(USER_ID)
                .friendId(FRIEND_ID)
                .status(STATUS)
                .createdAt(CREATED_AT)
                .build();
    }

    public static Friendship createFriendshipEntityFaker() {
        return Friendship.builder()
                .id(FAKER.number().randomNumber())
                .userId(FAKER.number().randomNumber())
                .friendId(FAKER.number().randomNumber())
                .status(FriendshipStatus.values()[FAKER.number().numberBetween(0, FriendshipStatus.values().length)])
                .createdAt(Instant.now())
                .build();
    }
}
