-- Tablas wishlist
CREATE TABLE wishlist (
    user_id BIGINT NOT NULL,
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_wishlist PRIMARY KEY (user_id)
);

CREATE TABLE wishlist_game (
    id BIGSERIAL PRIMARY KEY,
    game_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_wishlist_game_wishlist FOREIGN KEY (user_id) REFERENCES wishlist(user_id),
    CONSTRAINT uk_wishlist_game UNIQUE (user_id, game_id)
);