use `photo-contest`;

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

INSERT INTO users( user_credentials,first_name, last_name)
VALUES ('toshkata',  'Todor', 'Andonov'),
       ('vladi',  'Vladimir', 'Venkov'),
       ('nikolay9', 'Nikolay', 'Georgiev'),
       ('lybo270', 'Lyubomir', 'Denkov'),
       ('Peshkata',  'Petar', 'Raykov'),
       ('vankata123',  'Ivancho', 'Ivanov'),
       ('gero99',  'Gero', 'Ivanov'),
       ('pesho_loshiq',  'Petar', 'Petrov'),
       ('lerso1',  'Lerso', 'Kaloqnov'),
       ('kalincho',  'Kalin', 'Kirov'),
       ('ioan123',  'Ioan', 'Naumov'),
       ('koki123',  'Kaloqn', 'Terziev');

INSERT INTO users_roles(user_id, role_id)
VALUES
(1,2),
(2,2),
(3,2),
(4,2),
(5,1),
(6,1),
(7,1),
(8,1),
(9,1),
(10,1);

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

INSERT INTO points (points) VALUES
(20),(160),(25),(190);

INSERT INTO images (title, story, image,user_id ,points) VALUES
('how are you doing ','how are you doing '1,'https://i.imgur.com/JD4Auj5.png',1);

INSERT INTO user_points(user_id, points_id) VALUES
(5,1),
(6,2),
(7,3),
(8,4);