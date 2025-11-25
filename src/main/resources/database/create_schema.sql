CREATE SCHEMA IF NOT EXISTS turtle AUTHORIZATION turtle_user;

SET search_path TO turtle;

CREATE TABLE IF NOT EXISTS habitat_type (
        id SERIAL PRIMARY KEY,
        name VARCHAR(100) NOT NULL UNIQUE
    );

CREATE TABLE IF NOT EXISTS species (
        id SERIAL PRIMARY KEY,
        name VARCHAR(100) NOT NULL UNIQUE,
        habitat_type_id INT NOT NULL REFERENCES habitat_type(id)
    );

CREATE TABLE IF NOT EXISTS turtle (
        id SERIAL PRIMARY KEY,
        species_id INT NOT NULL REFERENCES species(id),
        name VARCHAR(100) NOT NULL,
        intake_date TIMESTAMPTZ NOT NULL,
        weight_grams INT NOT NULL,
        age_years INT NOT NULL,
        deleted BOOLEAN DEFAULT FALSE
    );
