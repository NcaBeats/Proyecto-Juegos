-- Tablas principales
CREATE TABLE estudio (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE genero (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE plataforma (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE juego (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE,
    descripcion VARCHAR(255) NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    fecha_lanzamiento DATE NOT NULL,
    estado VARCHAR(50) NOT NULL,
    fecha_registro TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    estudio_id BIGINT NOT NULL,
    CONSTRAINT fk_juego_estudio FOREIGN KEY (estudio_id) REFERENCES estudio(id)
);

CREATE TABLE juego_genero (
    juego_id BIGINT NOT NULL,
    genero_id BIGINT NOT NULL,
    PRIMARY KEY (juego_id, genero_id),
    CONSTRAINT fk_jg_juego FOREIGN KEY (juego_id) REFERENCES juego(id),
    CONSTRAINT fk_jg_genero FOREIGN KEY (genero_id) REFERENCES genero(id)
);

CREATE TABLE juego_plataforma (
    juego_id BIGINT NOT NULL,
    plataforma_id BIGINT NOT NULL,
    PRIMARY KEY (juego_id, plataforma_id),
    CONSTRAINT fk_jp_juego FOREIGN KEY (juego_id) REFERENCES juego(id),
    CONSTRAINT fk_jp_plataforma FOREIGN KEY (plataforma_id) REFERENCES plataforma(id)
);

-- Datos seed
INSERT INTO estudio (nombre, fecha_creacion) VALUES
    ('CD Projekt Red', CURRENT_TIMESTAMP),
    ('FromSoftware', CURRENT_TIMESTAMP),
    ('Santa Monica Studio', CURRENT_TIMESTAMP),
    ('Rockstar Games', CURRENT_TIMESTAMP);

INSERT INTO genero (nombre, fecha_creacion) VALUES
    ('RPG', CURRENT_TIMESTAMP),
    ('Acción', CURRENT_TIMESTAMP),
    ('Aventura', CURRENT_TIMESTAMP),
    ('Mundo Abierto', CURRENT_TIMESTAMP),
    ('Souls-like', CURRENT_TIMESTAMP);

INSERT INTO plataforma (nombre, fecha_creacion) VALUES
    ('PC', CURRENT_TIMESTAMP),
    ('PlayStation 5', CURRENT_TIMESTAMP),
    ('Xbox Series X', CURRENT_TIMESTAMP),
    ('Nintendo Switch', CURRENT_TIMESTAMP);

INSERT INTO juego (nombre, descripcion, precio, fecha_lanzamiento, estado, fecha_registro, estudio_id) VALUES
    ('The Witcher 3', 'RPG de mundo abierto con historia profunda', 29.99, '2015-05-19', 'ACTIVO', CURRENT_TIMESTAMP, 1),
    ('Cyberpunk 2077', 'Juego futurista de acción y rol', 49.99, '2020-12-10', 'ACTIVO', CURRENT_TIMESTAMP, 1),
    ('Elden Ring', 'RPG de acción con mundo abierto desafiante', 59.99, '2022-02-25', 'ACTIVO', CURRENT_TIMESTAMP, 2),
    ('God of War', 'Aventura épica basada en mitología nórdica', 39.99, '2018-04-20', 'ACTIVO', CURRENT_TIMESTAMP, 3),
    ('Red Dead Redemption 2', 'Mundo abierto en el viejo oeste americano', 59.99, '2018-10-26', 'ACTIVO', CURRENT_TIMESTAMP, 4);

INSERT INTO juego_genero (juego_id, genero_id) VALUES
    (1, 1), (1, 4), (1, 3),
    (2, 1), (2, 2), (2, 4),
    (3, 1), (3, 2), (3, 5),
    (4, 2), (4, 3),
    (5, 3), (5, 4), (5, 2);

INSERT INTO juego_plataforma (juego_id, plataforma_id) VALUES
    (1, 1), (1, 2), (1, 3),
    (2, 1), (2, 2), (2, 3),
    (3, 1), (3, 2), (3, 3),
    (4, 1), (4, 2),
    (5, 1), (5, 2), (5, 3);