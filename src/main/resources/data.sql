DROP TABLE IF EXISTS Clients;

CREATE TABLE Clients (
    id INT AUTO_INCREMENT  PRIMARY KEY,
    first_name VARCHAR(250) NOT NULL,
    last_name VARCHAR(250) NOT NULL,
    phone VARCHAR(250) DEFAULT NULL
);

INSERT INTO Clients (first_name, last_name, phone) VALUES
('Aliko', 'Dangote', '215343581'),
('Bill', 'Gates', '6841351854'),
('Folrunsho', 'Alakija', '534464684');