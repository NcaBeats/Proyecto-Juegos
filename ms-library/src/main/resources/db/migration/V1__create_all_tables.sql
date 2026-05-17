-- Tablas library
CREATE TABLE library (
    user_id BIGINT NOT NULL,
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_library PRIMARY KEY (user_id)
);

CREATE TABLE library_game (
    id BIGSERIAL PRIMARY KEY,
    game_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_library_game_library FOREIGN KEY (user_id) REFERENCES library(user_id),
    CONSTRAINT uk_library_game UNIQUE (user_id, game_id)
);