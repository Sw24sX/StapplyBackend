/* DROP TABLE IF EXISTS Clients;

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

DROP TABLE IF EXISTS APPMAIN;

create table APPMAIN (
    ID long auto_increment primary key,
    NAME varchar(250) not null,
    DEVELOPER varchar(250) not null,
    AVATAR_SRC text,
    IMAGE_SRC text
);

INSERT INTO APPMAIN(name, developer, AVATAR_SRC, IMAGE_SRC) VALUES
('Twitch', 'Twitch Interactive, Inc.',
 'https://play-lh.googleusercontent.com/QLQzL-MXtxKEDlbhrQCDw-REiDsA9glUH4m16syfar_KVLRXlzOhN7tmAceiPerv4Jg=s180-rw',
 'https://play-lh.googleusercontent.com/U--y9KFmvZ-N2IPc_QuFvjt9113Mh48Qn6GtxQBYjBpNtG-lR9nTd3AFFB8PKIqkkyA=w1920-h1095-rw'),
('Twitch_1', 'Twitch 1 Interactive, Inc.',
 'https://play-lh.googleusercontent.com/QLQzL-MXtxKEDlbhrQCDw-REiDsA9glUH4m16syfar_KVLRXlzOhN7tmAceiPerv4Jg=s180-rw',
 'https://play-lh.googleusercontent.com/U--y9KFmvZ-N2IPc_QuFvjt9113Mh48Qn6GtxQBYjBpNtG-lR9nTd3AFFB8PKIqkkyA=w1920-h1095-rw'),
('Twitch_2', 'Twitch 2 Interactive, Inc.',
 'https://play-lh.googleusercontent.com/QLQzL-MXtxKEDlbhrQCDw-REiDsA9glUH4m16syfar_KVLRXlzOhN7tmAceiPerv4Jg=s180-rw',
 'https://play-lh.googleusercontent.com/U--y9KFmvZ-N2IPc_QuFvjt9113Mh48Qn6GtxQBYjBpNtG-lR9nTd3AFFB8PKIqkkyA=w1920-h1095-rw'),
('Twitch_3', 'Twitch 3 Interactive, Inc.',
    'https://play-lh.googleusercontent.com/QLQzL-MXtxKEDlbhrQCDw-REiDsA9glUH4m16syfar_KVLRXlzOhN7tmAceiPerv4Jg=s180-rw',
    'https://play-lh.googleusercontent.com/U--y9KFmvZ-N2IPc_QuFvjt9113Mh48Qn6GtxQBYjBpNtG-lR9nTd3AFFB8PKIqkkyA=w1920-h1095-rw'),
('Twitch_4', 'Twitch 4 Interactive, Inc.',
    'https://play-lh.googleusercontent.com/QLQzL-MXtxKEDlbhrQCDw-REiDsA9glUH4m16syfar_KVLRXlzOhN7tmAceiPerv4Jg=s180-rw',
    'https://play-lh.googleusercontent.com/U--y9KFmvZ-N2IPc_QuFvjt9113Mh48Qn6GtxQBYjBpNtG-lR9nTd3AFFB8PKIqkkyA=w1920-h1095-rw'),
('Twitch_5', 'Twitch 5 Interactive, Inc.',
    'https://play-lh.googleusercontent.com/QLQzL-MXtxKEDlbhrQCDw-REiDsA9glUH4m16syfar_KVLRXlzOhN7tmAceiPerv4Jg=s180-rw',
    'https://play-lh.googleusercontent.com/U--y9KFmvZ-N2IPc_QuFvjt9113Mh48Qn6GtxQBYjBpNtG-lR9nTd3AFFB8PKIqkkyA=w1920-h1095-rw'),
('Twitch_6', 'Twitch 6 Interactive, Inc.',
    'https://play-lh.googleusercontent.com/QLQzL-MXtxKEDlbhrQCDw-REiDsA9glUH4m16syfar_KVLRXlzOhN7tmAceiPerv4Jg=s180-rw',
    'https://play-lh.googleusercontent.com/U--y9KFmvZ-N2IPc_QuFvjt9113Mh48Qn6GtxQBYjBpNtG-lR9nTd3AFFB8PKIqkkyA=w1920-h1095-rw'),
('Twitch_7', 'Twitch 7 Interactive, Inc.',
    'https://play-lh.googleusercontent.com/QLQzL-MXtxKEDlbhrQCDw-REiDsA9glUH4m16syfar_KVLRXlzOhN7tmAceiPerv4Jg=s180-rw',
    'https://play-lh.googleusercontent.com/U--y9KFmvZ-N2IPc_QuFvjt9113Mh48Qn6GtxQBYjBpNtG-lR9nTd3AFFB8PKIqkkyA=w1920-h1095-rw'),
('Twitch_8', 'Twitch 8 Interactive, Inc.',
    'https://play-lh.googleusercontent.com/QLQzL-MXtxKEDlbhrQCDw-REiDsA9glUH4m16syfar_KVLRXlzOhN7tmAceiPerv4Jg=s180-rw',
    'https://play-lh.googleusercontent.com/U--y9KFmvZ-N2IPc_QuFvjt9113Mh48Qn6GtxQBYjBpNtG-lR9nTd3AFFB8PKIqkkyA=w1920-h1095-rw'),
('Twitch_9', 'Twitch 9 Interactive, Inc. ',
 'https://play-lh.googleusercontent.com/QLQzL-MXtxKEDlbhrQCDw-REiDsA9glUH4m16syfar_KVLRXlzOhN7tmAceiPerv4Jg=s180-rw',
 'https://play-lh.googleusercontent.com/U--y9KFmvZ-N2IPc_QuFvjt9113Mh48Qn6GtxQBYjBpNtG-lR9nTd3AFFB8PKIqkkyA=w1920-h1095-rw'),
('Twitch_10', 'Twitch 10 Interactive, Inc.',
 'https://play-lh.googleusercontent.com/QLQzL-MXtxKEDlbhrQCDw-REiDsA9glUH4m16syfar_KVLRXlzOhN7tmAceiPerv4Jg=s180-rw',
 'https://play-lh.googleusercontent.com/U--y9KFmvZ-N2IPc_QuFvjt9113Mh48Qn6GtxQBYjBpNtG-lR9nTd3AFFB8PKIqkkyA=w1920-h1095-rw'); */