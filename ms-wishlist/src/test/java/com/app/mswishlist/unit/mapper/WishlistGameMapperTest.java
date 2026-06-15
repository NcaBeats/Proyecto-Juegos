package com.app.mswishlist.unit.mapper;

import com.app.mswishlist.wishlistgame.mapper.WishlistGameMapper;
import com.app.mswishlist.wishlistgame.model.WishlistGame;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.app.mswishlist.support.WishlistFactory.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class WishlistGameMapperTest {
    @InjectMocks
    WishlistGameMapper wishlistGameMapper;

    @Test
    void toEntity_and_toResponse() {
        WishlistGame entity = wishlistGameMapper.toEntity(createWishlistGameRequest(), createWishlist());
        assertNotNull(entity);
        assertEquals(GAME_ID, entity.getGameId());
    }

    @Test
    void toResponse_MapsEntityAndJuegoResponseToDto() {
        var response = wishlistGameMapper.toResponse(createWishlistGameEntity(), createJuegoResponse());
        assertNotNull(response);
        assertEquals(createWishlistGameEntity().getGameId(), response.gameId());
    }
}
