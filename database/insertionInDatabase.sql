use
`photo-contest`;

INSERT INTO contest_phase(name)
VALUES ('ongoing'),
       ('voting'),
       ('finished');

INSERT INTO roles(name)
VALUES ('user'),
       ('organizer');

INSERT INTO contest_type(name)
VALUES ('open'),
       ('invitational');

INSERT INTO user_credentials(user_name, password)
VALUES ('nikolay9', '12345678'),
       ('lybo270', '12345678'),
       ('toshkata', '12345678'),
       ('vladi', '12345678'),
       ('nikolaicho', '12345678'),
       ('Peshkata', '12345678'),
       ('gero99', '12345678'),
       ('vankata123', '12345678'),
       ('pesho_loshiq', '12345678'),
       ('lerso1', '12345678'),
       ('kalincho', '12345678'),
       ('ioan123', '12345678'),
       ('koki123', '12345678'),
       ('zafir44','12345678'),
       ('metin','12345678'),
       ('eler', '12345678'),
       ('shkeler','12345678'),
       ('shmeker','12345678'),
       ('djaro', '12345678'),
       ('kukata','12345678'),
       ('ivaka','12345678'),
       ('jeko', '12345678'),
       ('loli','12345678' ),
       ('jeni','12345678' ),
       ('kati','12345678' ),
       ('matei','12345678'),
       ('fatka99','12345678' ),
       ('krisi','12345678' ),
       ('tiko','12345678' ),
       ('miko', '12345678'),
       ('forfi', '12345678'),
       ('dero33', '12345678'),
       ('heimdown','12345678' ),
       ('dragon77', '12345678'),
       ('koftimofti22','12345678' ),
       ('kosuma','12345678' ),
       ('lerko222', '12345678'),
       ('picha79', '12345678');

INSERT INTO users(user_credentials, first_name, last_name)
VALUES ('toshkata', 'Todor', 'Andonov'),
       ('vladi', 'Vladimir', 'Venkov'),
       ('nikolay9', 'Nikolay', 'Georgiev'),
       ('lybo270', 'Lyubomir', 'Denkov'),
       ('Peshkata', 'Petar', 'Raykov'),
       ('vankata123', 'Ivancho', 'Ivanov'),
       ('gero99', 'Gero', 'Ivanov'),
       ('pesho_loshiq', 'Petar', 'Petrov'),
       ('lerso1', 'Lerso', 'Kaloqnov'),
       ('kalincho', 'Kalin', 'Kirov'),
       ('ioan123', 'Ioan', 'Naumov'),
       ('koki123', 'Koki', 'Terziev'),
       ('zafir44', 'Zafirc', 'Terziev'),
       ('metin', 'Metin', 'Metinov'),
       ('eler', 'Eler', 'Terziev'),
       ('shkeler', 'Skeler', 'Kirochov'),
       ('shmeker', 'Shmeker', 'Shmekerov'),
       ('djaro', 'Djaro', 'Ivanov'),
       ('kukata', 'Kukata', 'Georgiev'),
       ('ivaka', 'Ivo', 'Andonov'),
       ('jeko', 'Jeko', 'Terziev'),
       ('loli', 'Loli', 'Georgiev'),
       ('jeni', 'Jeni', 'Terziev'),
       ('kati', 'Kati', 'Georgiev'),
       ('matei', 'Matei', 'Terziev'),
       ('fatka99', 'Fatkata', 'Jikov'),
       ('krisi', 'Krisi', 'Georgiev'),
       ('tiko', 'Tikorch', 'Ivanov'),
       ('miko', 'Miko', 'Mikov'),
       ('forfi', 'Forfi', 'Forfiev'),
       ('dero33', 'Derzan', 'Derzanov'),
       ('heimdown', 'Heimdown', 'Kiorhev'),
       ('dragon77', 'Listo', 'Listov'),
       ('koftimofti22', 'Deqn', 'Deqnov'),
       ('kosuma', 'Uri', 'Uriov'),
       ('lerko222', 'Lerko', 'Lerkov'),
       ('picha79', 'Genadi', 'Genadiev');

INSERT INTO users_roles(user_id, role_id)
VALUES (1, 2),
       (2, 2),
       (3, 2),
       (4, 2),
       (5, 1),
       (6, 1),
       (7, 1),
       (8, 1),
       (9, 1),
       (10, 1),
       (11, 1),
       (12, 1),
       (13, 1),
       (14, 1),
       (15, 1),
       (16, 1),
       (17, 1),
       (18, 1),
       (19, 1),
       (20, 1),
       (21, 1),
       (22, 1),
       (23, 1),
       (24, 1),
       (25, 1),
       (26, 1),
       (27, 1),
       (28, 1),
       (29, 1),
       (30, 1),
       (31, 1),
       (32, 1),
       (33, 1),
       (34, 1),
       (35, 1),
       (36, 1),
       (37, 1);
INSERT INTO category(name)
VALUES ('Nature'),
       ('Pets'),
       ('Dogs'),
       ('Cats'),
       ('Cities'),
       ('Mountains');

INSERT INTO category(name)
VALUES ('nature');

INSERT INTO points (points)
VALUES (20),
       (160),
       (25),
       (44),
       (152),
       (70),
       (70),
       (70),
       (70),
       (70),
       (70),
       (70),
       (0),
       (0),
       (0),
       (0),
       (0),
       (0),
       (0),
       (0),
       (0),
       (0),
       (0),
       (0),
       (0),
       (0),
       (0),
       (0),
       (0),
       (0),
       (0),
       (0),
       (0);

INSERT INTO images (title, story, image, user_id, points)
VALUES ('how are you doing ', 'how are you doing ', 'https://i.imgur.com/JD4Auj5.png', 5, 5),
       ('how are you doing ', 'how are you doing ', 'https://i.imgur.com/JD4Auj5.png', 6, 10),
       ('how are you doing ', 'how are you doing ', 'https://i.imgur.com/JD4Auj5.png', 7, 15),
       ('how are you doing ', 'how are you doing ', 'https://i.imgur.com/JD4Auj5.png', 8, 20),
       ('how are you doing ', 'how are you doing ', 'https://i.imgur.com/JD4Auj5.png', 9, 14),
       ('how are you doing ', 'how are you doing ', 'https://i.imgur.com/JD4Auj5.png', 10, 17),
       ('how are you doing ', 'how are you doing ', 'https://i.imgur.com/JD4Auj5.png', 11, 21),
       ('how are you doing ', 'how are you doing ', 'https://i.imgur.com/JD4Auj5.png', 12, 6),
       ('how are you doing ', 'how are you doing ', 'https://i.imgur.com/JD4Auj5.png', 13, 3),
       ('how are you doing ', 'how are you doing ', 'https://i.imgur.com/JD4Auj5.png', 14, 5),
       ('how are you doing ', 'how are you doing ', 'https://i.imgur.com/JD4Auj5.png', 15, 1);


INSERT INTO users_images(user_id, image_id)
VALUES (5,1),
       (6,2),
       (7,3),
       (8,4),
       (9,5),
       (10,6),
       (11,7),
       (12,8),
       (13,9),
       (14,10),
       (15,11);

INSERT INTO contest(title, category_id, first_phase, second_phase, user_id, type_id, image_url, phase_id)
VALUES
('Dogs',1,'2021-04-20 18:52:35','2021-04-21 18:52:35',1,1,'https://i.imgur.com/JD4Auj5.png',1),
('Dogs',1,'2021-04-27 18:52:35','2021-04-28 18:52:35',1,1,'https://i.imgur.com/JD4Auj5.png',1),
('Dogs',1,'2021-04-10 18:52:35','2021-04-30 18:52:35',1,1,'https://i.imgur.com/JD4Auj5.png',1),
('Dogs',1,'2021-04-11 18:52:35','2021-04-30 18:52:35',1,1,'https://i.imgur.com/JD4Auj5.png',1),
('Dogs',1,'2021-04-24 18:52:35','2021-04-15 18:52:35',1,1,'https://i.imgur.com/JD4Auj5.png',1),
('Dogs',1,'2021-04-24 18:52:35','2021-04-15 18:52:35',1,1,'https://i.imgur.com/JD4Auj5.png',1),
('Dogs',1,'2021-04-24 18:52:35','2021-04-15 18:52:35',1,1,'https://i.imgur.com/JD4Auj5.png',1);

INSERT INTO user_points(user_id, points_id)
VALUES (5, 1),
       (6, 2),
       (7, 3),
       (8, 4),
       (9, 5),
       (10, 6),
       (11, 7),
       (12, 8),
       (13, 9),
       (14, 10),
       (15, 11),
       (16, 12),
       (17, 13),
       (18, 14),
       (19, 15),
       (20, 16),
       (21, 17),
       (22, 18),
       (23, 19),
       (24, 20),
       (25, 21),
       (26, 22),
       (27, 23),
       (28, 24),
       (29, 25),
       (30, 26),
       (31, 27),
       (32, 28),
       (33, 29),
       (34, 30),
       (35, 31),
       (36, 32),
       (37, 33);