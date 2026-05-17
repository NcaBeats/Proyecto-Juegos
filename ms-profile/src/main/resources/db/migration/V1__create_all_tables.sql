-- Tabla profile
CREATE TABLE profile (
    user_id BIGINT NOT NULL,
    nickname VARCHAR(30) NOT NULL UNIQUE,
    avatar VARCHAR(255),
    bio VARCHAR(150),
    tipo_perfil VARCHAR(20) NOT NULL,
    fecha_registro TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_profile PRIMARY KEY (user_id)
);