-- Tabla usuario
CREATE TABLE usuario (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(25) NOT NULL,
    email VARCHAR(100) NOT NULL,
    saldo DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
    fecha_creacion TIMESTAMP NOT NULL,
    CONSTRAINT uk_email UNIQUE (email),
    CONSTRAINT uk_nombre UNIQUE (nombre)
);

-- Datos seed
INSERT INTO usuario (nombre, email, saldo, fecha_creacion) VALUES
    ('Juan Perez', 'juan.perez@mail.com', 100.00, CURRENT_TIMESTAMP),
    ('Maria Gonzalez', 'maria.gonzalez@mail.com', 100.00, CURRENT_TIMESTAMP),
    ('Carlos Rojas', 'carlos.rojas@mail.com', 100.00, CURRENT_TIMESTAMP),
    ('Fernanda Soto', 'fernanda.soto@mail.com', 100.00, CURRENT_TIMESTAMP),
    ('Diego Muñoz', 'diego.munoz@mail.com', 100.00, CURRENT_TIMESTAMP);