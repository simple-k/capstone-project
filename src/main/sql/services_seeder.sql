USE simple_k_db;

SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE services;
SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO services (service)
VALUES ('ESL'),
       ('Disability'),
       ('Bus Transportation');