package com.app.msfriendship.mapper;

import com.app.msfriendship.dto.FriendshipResponse;
import com.app.msfriendship.model.Friendship;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FriendshipMapper {
    FriendshipResponse toResponse(Friendship friendship);
}