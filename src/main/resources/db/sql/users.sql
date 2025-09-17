--changeset rusile:users:create_table
CREATE TABLE IF NOT EXISTS users
(
    id         UUID PRIMARY KEY,
    surname    VARCHAR(255) NOT NULL,
    name       VARCHAR(255) NOT NULL,
    birth_date DATE         NOT NULL,
    city       VARCHAR(255) NOT NULL,
    biography  VARCHAR(511)
);