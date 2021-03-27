use
`photo-contest`;

INSERT INTO users(user_name, email, first_name, last_name, password, `rank`, points)
VALUES ('nikolay9', 'nikolaypnc@gmail.com', 'Nikolay', 'Georgiev', '12345678', 'junkie', 1000),
       ('toshkata', 'Todor.andonov.telerik@gmail.bg', 'Todor', 'Andonov', '12345678', 'junkie', 1),
       ('vladi', 'vladimir.venkov.telerik@gmail.bg', 'Vladimir', 'Venkov', '12345678', 'junkie', 1),
       ('nikolaicho', 'nikolay.georgiev.@gmail.bg', 'Nikolay', 'Georgiev', '12345678', 'junkie', 1),
       ('Peshkata', 'Petar.Raykov.telerik.@gmail.bg', 'Petar', 'Raykov', '12345678', 'junkie', 1),
       ('lubaka123', 'lybo270@gmail.com', 'Lyubomir', 'Denkov', '12345678', 'junkie', 8),
       ('vankata123', 'ivanivanon@gmail.com', 'Ivancho', 'Ivanov', '12345678', 'junkie', 9),
       ('gero99', 'geroivanon@gmail.com', 'Gero', 'Ivanov', '12345678', 'junkie', 10),
       ('pesho_loshiq', 'petarpetrov@gmail.com', 'Petar', 'Petrov', '12345678', 'junkie', 11),
       ('lerso1', 'lersokaloqnov@gmail.com', 'Lerso', 'Kaloqnov', '12345678', 'junkie', 2),
       ('kalincho', 'something@gmail.com', 'Kalin', 'Kirov', '12345678', 'junkie', 4),
       ('ioan123', 'ioannaumov@gmail.com', 'Ioan', 'Naumov', '12345678', 'junkie', 1),
       ('koki123', 'silniq@gmail.com', 'Kaloqn', 'Terziev', '12345678', 'junkie', 1);

INSERT INTO category(name)
VALUES ('Nature'),
       ('Pets'),
       ('Dogs'),
       ('Cats'),
       ('Cities'),
       ('Mountains');

INSERT INTO roles(name)
VALUES ('user'),
       ('organizer'),
       ('admin');

INSERT INTO users_roles(user_id, role_id)
VALUES (1, 3),
       (2, 3),
       (3, 3),
       (4, 3),
       (5, 3),
       (6, 3),
       (7, 1),
       (8, 1),
       (9, 2),
       (10, 2),
       (11, 1),
       (12, 1);
