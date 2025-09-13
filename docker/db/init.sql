CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL,
    surname VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    birth_date DATE NOT NULL,
    city varchar(255) NOT NULL,
)