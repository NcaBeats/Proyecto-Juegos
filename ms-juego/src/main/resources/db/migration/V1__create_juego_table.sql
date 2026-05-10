-- V1__init.sql

CREATE TABLE estudio (
                         id          BIGINT AUTO_INCREMENT PRIMARY KEY,
                         nombre      VARCHAR(255) NOT NULL,
                         fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE genero (
                        id          BIGINT AUTO_INCREMENT PRIMARY KEY,
                        nombre      VARCHAR(255) NOT NULL,
                        fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE plataforma (
                            id          BIGINT AUTO_INCREMENT PRIMARY KEY,
                            nombre      VARCHAR(255) NOT NULL,
                            fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE juego (
                       id                  BIGINT AUTO_INCREMENT PRIMARY KEY,
                       nombre              VARCHAR(50)    NOT NULL UNIQUE,
                       descripcion         VARCHAR(255)   NOT NULL,
                       precio              DECIMAL(10,2)  NOT NULL,
                       fecha_lanzamiento   DATE           NOT NULL,
                       estado              VARCHAR(50)    NOT NULL,
                       fecha_registro      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       estudio_id          BIGINT         NOT NULL,
                       CONSTRAINT fk_juego_estudio FOREIGN KEY (estudio_id) REFERENCES estudio(id)
);

CREATE TABLE juego_genero (
                              juego_id    BIGINT NOT NULL,
                              genero_id   BIGINT NOT NULL,
                              PRIMARY KEY (juego_id, genero_id),
                              CONSTRAINT fk_jg_juego  FOREIGN KEY (juego_id)  REFERENCES juego(id),
                              CONSTRAINT fk_jg_genero FOREIGN KEY (genero_id) REFERENCES genero(id)
);

CREATE TABLE juego_plataforma (
                                  juego_id      BIGINT NOT NULL,
                                  plataforma_id BIGINT NOT NULL,
                                  PRIMARY KEY (juego_id, plataforma_id),
                                  CONSTRAINT fk_jp_juego     FOREIGN KEY (juego_id)      REFERENCES juego(id),
                                  CONSTRAINT fk_jp_plataforma FOREIGN KEY (plataforma_id) REFERENCES plataforma(id)
);