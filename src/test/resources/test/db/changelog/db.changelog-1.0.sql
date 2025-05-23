-- liquibase formatted sql

-- changeset weather-dev:1
CREATE TABLE IF NOT EXISTS users (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY,
    login VARCHAR(64) NOT NULL,
    password VARCHAR(72) NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE users ADD COLUMN login_lower VARCHAR(64) GENERATED ALWAYS AS (LOWER(login));

CREATE UNIQUE INDEX unique_users_login_idx ON users(login_lower);

-- changeset weather-dev:2
CREATE TABLE IF NOT EXISTS locations (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY,
    name VARCHAR(64) NOT NULL,
    user_id BIGINT NOT NULL,
    latitude DECIMAL(2,4) NOT NULL,
    longitude DECIMAL(3,4) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    UNIQUE (user_id, latitude, longitude)
);

-- changeset weather-dev:3
CREATE TABLE IF NOT EXISTS sessions (
    id UUID,
    user_id BIGINT NOT NULL,
    expires_at TIMESTAMP NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);