-- V2__seed.sql

-- Estudios
INSERT INTO estudio (nombre, fecha_creacion) VALUES
                                                 ('CD Projekt Red',  CURRENT_TIMESTAMP),
                                                 ('FromSoftware',    CURRENT_TIMESTAMP),
                                                 ('Santa Monica Studio', CURRENT_TIMESTAMP),
                                                 ('Rockstar Games',  CURRENT_TIMESTAMP);

-- Géneros
INSERT INTO genero (nombre, fecha_creacion) VALUES
                                                ('RPG',             CURRENT_TIMESTAMP),
                                                ('Acción',          CURRENT_TIMESTAMP),
                                                ('Aventura',        CURRENT_TIMESTAMP),
                                                ('Mundo Abierto',   CURRENT_TIMESTAMP),
                                                ('Souls-like',      CURRENT_TIMESTAMP);

-- Plataformas
INSERT INTO plataforma (nombre, fecha_creacion) VALUES
                                                    ('PC',          CURRENT_TIMESTAMP),
                                                    ('PlayStation 5', CURRENT_TIMESTAMP),
                                                    ('Xbox Series X', CURRENT_TIMESTAMP),
                                                    ('Nintendo Switch', CURRENT_TIMESTAMP);

-- Juegos
-- estudio_id: 1=CD Projekt, 2=FromSoftware, 3=Santa Monica, 4=Rockstar
INSERT INTO juego (nombre, descripcion, precio, fecha_lanzamiento, estado, fecha_registro, estudio_id) VALUES
                                                                                                           ('The Witcher 3',        'RPG de mundo abierto con historia profunda',       29.99, '2015-05-19', 'ACTIVO', CURRENT_TIMESTAMP, 1),
                                                                                                           ('Cyberpunk 2077',       'Juego futurista de acción y rol',                  49.99, '2020-12-10', 'ACTIVO', CURRENT_TIMESTAMP, 1),
                                                                                                           ('Elden Ring',           'RPG de acción con mundo abierto desafiante',       59.99, '2022-02-25', 'ACTIVO', CURRENT_TIMESTAMP, 2),
                                                                                                           ('God of War',           'Aventura épica basada en mitología nórdica',       39.99, '2018-04-20', 'ACTIVO', CURRENT_TIMESTAMP, 3),
                                                                                                           ('Red Dead Redemption 2','Mundo abierto en el viejo oeste americano',        59.99, '2018-10-26', 'ACTIVO', CURRENT_TIMESTAMP, 4);

-- juego_genero
-- juego 1: The Witcher 3 → RPG, Mundo Abierto, Aventura
INSERT INTO juego_genero (juego_id, genero_id) VALUES (1,1),(1,4),(1,3);
-- juego 2: Cyberpunk 2077 → RPG, Acción, Mundo Abierto
INSERT INTO juego_genero (juego_id, genero_id) VALUES (2,1),(2,2),(2,4);
-- juego 3: Elden Ring → RPG, Acción, Souls-like
INSERT INTO juego_genero (juego_id, genero_id) VALUES (3,1),(3,2),(3,5);
-- juego 4: God of War → Acción, Aventura
INSERT INTO juego_genero (juego_id, genero_id) VALUES (4,2),(4,3);
-- juego 5: RDR2 → Aventura, Mundo Abierto, Acción
INSERT INTO juego_genero (juego_id, genero_id) VALUES (5,3),(5,4),(5,2);

-- juego_plataforma
-- juego 1: The Witcher 3 → PC, PS5, Xbox
INSERT INTO juego_plataforma (juego_id, plataforma_id) VALUES (1,1),(1,2),(1,3);
-- juego 2: Cyberpunk 2077 → PC, PS5, Xbox
INSERT INTO juego_plataforma (juego_id, plataforma_id) VALUES (2,1),(2,2),(2,3);
-- juego 3: Elden Ring → PC, PS5, Xbox
INSERT INTO juego_plataforma (juego_id, plataforma_id) VALUES (3,1),(3,2),(3,3);
-- juego 4: God of War → PC, PS5
INSERT INTO juego_plataforma (juego_id, plataforma_id) VALUES (4,1),(4,2);
-- juego 5: RDR2 → PC, PS5, Xbox
INSERT INTO juego_plataforma (juego_id, plataforma_id) VALUES (5,1),(5,2),(5,3);