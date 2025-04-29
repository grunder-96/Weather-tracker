-- liquibase formatted sql

-- changeset weather-dev:1
ALTER TABLE locations ALTER COLUMN latitude TYPE DECIMAL(6, 4);

ALTER TABLE locations ALTER COLUMN longitude TYPE DECIMAL(7, 4);