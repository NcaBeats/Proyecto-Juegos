CREATE TABLE juego (
                       id BIGINT AUTO_INCREMENT,
                       nombre VARCHAR(50) NOT NULL,
                       descripcion VARCHAR(255) NOT NULL,
                       precio DECIMAL(10,2) NOT NULL,
                       fecha_lanzamiento DATE NOT NULL,
                       estado VARCHAR(50) NOT NULL,
                       fecha_registro TIMESTAMP NOT NULL,

                       CONSTRAINT pk_juego PRIMARY KEY (id),
                       CONSTRAINT uk_juego_nombre UNIQUE (nombre)
);