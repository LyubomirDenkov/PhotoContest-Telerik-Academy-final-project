use `photo-contest`;

INSERT INTO contest_phase(name)
VALUES ('preparing'),
       ('voting'),
       ('finished');

INSERT INTO roles(name)
VALUES ('user'),
       ('organizer');

INSERT INTO contest_type(name)
VALUES ('open'),
       ('invitational');

INSERT INTO user_credentials(user_name, password)
VALUES ('nikolay9','12345678'),
       ('lybo270','12345678'),
       ('toshkata','12345678'),
       ('vladi','12345678'),
       ('nikolaicho','12345678'),
       ('Peshkata','12345678'),
       ('gero99','12345678'),
       ('vankata123','12345678'),
       ('pesho_loshiq','12345678'),
       ('lerso1','12345678'),
       ('kalincho','12345678'),
       ('ioan123','12345678'),
       ('koki123','12345678');

INSERT INTO users( user_credentials,first_name, last_name, points)
VALUES ('toshkata',  'Todor', 'Andonov',  200),
       ('vladi',  'Vladimir', 'Venkov',   1),
       ('nikolaicho', 'Nikolay', 'Georgiev',   1),
       ('Peshkata',  'Petar', 'Raykov',  1),
       ('vankata123',  'Ivancho', 'Ivanov',  9),
       ('gero99',  'Gero', 'Ivanov',  10),
       ('pesho_loshiq',  'Petar', 'Petrov',  11),
       ('lerso1',  'Lerso', 'Kaloqnov', 2),
       ('kalincho',  'Kalin', 'Kirov',  4),
       ('ioan123',  'Ioan', 'Naumov',  1),
       ('koki123',  'Kaloqn', 'Terziev',  1);

INSERT INTO organizers(user_credentials, first_name, last_name)
VALUES ('nikolay9', 'Nikolay', 'Georgiev'),
       ('lybo270',  'Lyubomir', 'Denkov');

INSERT INTO users_roles(user_credentials, role_id)
VALUES ('nikolay9',2),
       ('lybo270',2),
       ('toshkata',1),
       ('vladi',1),
       ('nikolaicho',1),
       ('Peshkata',1),
       ('vankata123',1),
       ('gero99',1),
       ('pesho_loshiq',1),
       ('lerso1',1),
       ('kalincho',1),
       ('ioan123',1),
       ('koki123',1);

INSERT INTO category(name)
VALUES ('Nature'),
       ('Pets'),
       ('Dogs'),
       ('Cats'),
       ('Cities'),
       ('Mountains');

INSERT INTO category(name)
VALUES ('nature');

INSERT INTO contest_type(name)
VALUES ('open');

INSERT INTO images (title, story, image, user_id, points) VALUES
('how are you doing ','how are you doing ','https://i.imgur.com/JD4Auj5.png',1,50),
('how are you doing ','how are you doing ','https://i.imgur.com/JD4Auj5.png',2,34),
('how are you doing ','how are you doing ','https://i.imgur.com/JD4Auj5.png',2,82),
('how are you doing ','how are you doing ','https://i.imgur.com/JD4Auj5.png',3,80),
('how are you doing ','how are you doing ','https://i.imgur.com/JD4Auj5.png',1,59),
('how are you doing ','how are you doing ','https://i.imgur.com/JD4Auj5.png',4,90),
('how are you doing ','how are you doing ','https://i.imgur.com/JD4Auj5.png',1,30),
('how are you doing ','how are you doing ','https://i.imgur.com/JD4Auj5.png',2,70),
('how are you doing ','how are you doing ','https://i.imgur.com/JD4Auj5.png',1,60);