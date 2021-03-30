use `photo-contest`;

INSERT INTO ranks(name)
VALUES ('junkie'),('enthusiast'),('master'),('dictator');

INSERT INTO contest_phase(name)
VALUES ('phase |'),
       ('phase ||'),
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

INSERT INTO users( user_credentials,first_name, last_name, rank_id, points)
VALUES ('toshkata',  'Todor', 'Andonov',  1, 1),
       ('vladi',  'Vladimir', 'Venkov',  1, 1),
       ('nikolaicho', 'Nikolay', 'Georgiev',  1, 1),
       ('Peshkata',  'Petar', 'Raykov',  1, 1),
       ('vankata123',  'Ivancho', 'Ivanov', 1, 9),
       ('gero99',  'Gero', 'Ivanov', 1, 10),
       ('pesho_loshiq',  'Petar', 'Petrov', 1, 11),
       ('lerso1',  'Lerso', 'Kaloqnov',  1, 2),
       ('kalincho',  'Kalin', 'Kirov',  1, 4),
       ('ioan123',  'Ioan', 'Naumov',  1, 1),
       ('koki123',  'Kaloqn', 'Terziev', 1, 1);

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