-- Tabla review
CREATE TABLE review (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    juego_id BIGINT NOT NULL,
    comentario VARCHAR(500) NOT NULL,
    rating VARCHAR(20) NOT NULL,
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);