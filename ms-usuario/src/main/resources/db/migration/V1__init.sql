CREATE TABLE usuario (
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    nombre VARCHAR(25) NOT NULL,
    email VARCHAR(100) NOT NULL ,
    fecha_creacion TIMESTAMP NOT NULL,
    CONSTRAINT pk_usuario PRIMARY KEY (id),
    CONSTRAINT uk_email UNIQUE (email)
);