SET search_path TO turtle;

INSERT INTO habitat_type (name)
VALUES ('sósvíz');
INSERT INTO habitat_type (name)
VALUES ('brakk víz');
INSERT INTO habitat_type (name)
VALUES ('édesvíz');

INSERT INTO species (name, habitat_type_id)
VALUES ('Mocsári teknős', (SELECT id FROM habitat_type WHERE name = 'sósvíz')),
       ('Közönséges tarajos teknős', (SELECT id FROM habitat_type WHERE name = 'édesvíz')),
       ('Sárgafülű ékszerteknős', (SELECT id FROM habitat_type WHERE name = 'brakk víz'));

INSERT INTO turtle (species_id, name, intake_date, weight_grams, age_years)
VALUES ((SELECT id FROM species WHERE name = 'Közönséges tarajos teknős'),
        'Misi',
        '2024-03-15T10:30:00+01:00',
        1200,
        4);
