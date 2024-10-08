CREATE TABLE IF NOT EXISTS category
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(255),
    description VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS product
(
    id                 SERIAL PRIMARY KEY,
    name               VARCHAR(255),
    description        VARCHAR(255),
    available_quantity DOUBLE PRECISION NOT NULL,
    price              NUMERIC(38, 2),
    category_id        integer
        constraint fk_product_category_id references category (id)
);

