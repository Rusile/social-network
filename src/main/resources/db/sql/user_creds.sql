--liquibase formatted sql

--changeset rusile:user_creds:create_table
CREATE TABLE IF NOT EXISTS user_creds
(
    user_id       UUID PRIMARY KEY,
    password_hash VARCHAR(255) NOT NULL
);