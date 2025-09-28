--liquibase formatted sql

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

--changeset rusile:users:name_surname_idx
CREATE EXTENSION IF NOT EXISTS pg_trgm;

CREATE INDEX IF NOT EXISTS idx_users_name_surname_trgm
    ON users USING gin (name gin_trgm_ops, surname gin_trgm_ops);