-- Tablas purchase
CREATE TABLE purchase (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    total_precio DECIMAL(10,2) NOT NULL,
    fecha_compra TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE purchase_game (
    id BIGSERIAL PRIMARY KEY,
    game_id BIGINT NOT NULL,
    purchase_id BIGINT NOT NULL,
    fecha_registro TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_purchase_game_purchase FOREIGN KEY (purchase_id) REFERENCES purchase(id)
);