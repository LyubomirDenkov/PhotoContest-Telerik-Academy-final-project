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
VALUES ('toshkata', '12345678'),
       ('vladi', '12345678'),
       ('nikolay9', '12345678'),
       ('lybo270', '12345678'),
       ('Peshkata', '12345678'),
       ('vankata123', '12345678'),
       ('gero99', '12345678'),
       ('pesho_88', '12345678'),
       ('Xavier', '12345678'),
       ('turgoveca72', '12345678'),
       ('hladenRazum66', '12345678'),
       ('koki123', '12345678'),
       ('zafir44', '12345678'),
       ('metin', '12345678'),
       ('Dominic', '12345678'),
       ('merenge77', '12345678'),
       ('Skyline', '12345678'),
       ('RomanBro22', '12345678'),
       ('Tehnika', '12345678'),
       ('Ohao', '12345678'),
       ('jeko', '12345678'),
       ('loli', '12345678'),
       ('jeni', '12345678'),
       ('kati2', '12345678'),
       ('matei', '12345678'),
       ('fatka99', '12345678'),
       ('krisi', '12345678'),
       ('tiko', '12345678'),
       ('miko', '12345678'),
       ('forfi', '12345678'),
       ('dero33', '12345678'),
       ('heimdown', '12345678'),
       ('dragon77', '12345678'),
       ('kukata', '12345678'),
       ('kosuma', '12345678'),
       ('lerko222', '12345678'),
       ('picha79', '12345678');

INSERT INTO users(user_credentials, first_name, last_name, profileImage)
VALUES ('toshkata', 'Todor', 'Andonov', 'https://i.imgur.com/QL6iC9J.jpg'),
       ('vladi', 'Vladimir', 'Venkov', 'https://i.imgur.com/7kgCdSz.jpg'),
       ('nikolay9', 'Nikolay', 'Georgiev', 'https://i.imgur.com/kIMzKak.jpg'),
       ('lybo270', 'Lyubomir', 'Denkov', 'https://i.imgur.com/Wz9xAXZ.jpg'),
       ('Peshkata', 'Petar', 'Raykov', 'https://i.imgur.com/tkzR9wE.jpg'),
       ('vankata123', 'Ivancho', 'Ivanov', 'https://i.imgur.com/iKZuI00.jpg'),
       ('gero99', 'Gero', 'Ivanov', 'https://i.imgur.com/3NhLZAp.jpg'),
       ('pesho_88', 'Petar', 'Petrov', 'https://i.imgur.com/HPuMXJT.jpg'),
       ('Xavier', 'Xavier', 'Maroto', 'https://i.imgur.com/lwqbUQT.jpg'),
       ('turgoveca72', 'Antonio', 'Resio', 'https://i.imgur.com/bbwlIxL.jpg'),
       ('hladenRazum66', 'Mariano', 'Delgado', 'https://i.imgur.com/NdZFAvH.jpg'),
       ('koki123', 'Koki', 'Terziev', 'https://i.imgur.com/BzXTIAk.jpg'),
       ('zafir44', 'Zafirc', 'Terziev', 'https://i.imgur.com/Ff3M8i8.jpg'),
       ('metin', 'Metin', 'Metinov', 'https://i.imgur.com/xl45fLn.jpg'),
       ('Dominic', 'Dominic', 'Torreto', 'https://i.imgur.com/J4yAsgJ.jpg'),
       ('merenge77', 'Amador', 'Rivas', 'https://i.imgur.com/4IMdXfx.jpg'),
       ('Skyline', 'Paul', 'Walker', 'https://i.imgur.com/Aus0rum.jpg'),
       ('RomanBro22', 'Roman', 'Piurs', 'https://i.imgur.com/XATeoAS.jpg'),
       ('Tehnika', 'Tej', 'Parker', 'https://i.imgur.com/P6WkMEu.jpg'),
       ('Ohao', 'Ohi', 'Genro', 'https://i.imgur.com/4SrvQDb.jpg'),
       ('jeko', 'Jeko', 'Terziev', 'https://i.imgur.com/6eW4QGD.jpg'),
       ('loli', 'Loli', 'Georgiev', 'https://i.imgur.com/U1uR1LO.jpg'),
       ('jeni', 'Jeni', 'Terziev', 'https://i.imgur.com/dpqaIRH.jpg'),
       ('kati2', 'Katit', 'Georgiev', 'https://i.imgur.com/opfVxeJ.jpg'),
       ('matei', 'Matei', 'Terziev', 'https://i.imgur.com/avV24ik.jpg'),
       ('fatka99', 'Fatkata', 'Jikov', 'https://i.imgur.com/NYjiypg.jpg'),
       ('krisi', 'Krisi', 'Georgiev', 'https://i.imgur.com/AvRMQhS.jpg'),
       ('tiko', 'Tikorch', 'Ivanov', 'https://i.imgur.com/jywCnNs.jpg'),
       ('miko', 'Miko', 'Mikov', 'https://i.imgur.com/E4i7I0F.jpg'),
       ('forfi', 'Forfi', 'Forfiev', 'https://i.imgur.com/5F89Ckb.jpg'),
       ('dero33', 'Derzan', 'Derzanov', 'https://i.imgur.com/oJQOqmc.jpg'),
       ('heimdown', 'Heimdown', 'Kiorhev', 'https://i.imgur.com/boxwpQC.jpg'),
       ('dragon77', 'Listo', 'Listov', 'https://i.imgur.com/dRhhwA1.jpg'),
       ('kukata', 'Deqn', 'Deqnov', 'https://i.imgur.com/7536SeP.jpg'),
       ('kosuma', 'Uri', 'Uriov', 'https://i.imgur.com/jLlaKM6.jpg'),
       ('lerko222', 'Lerko', 'Lerkov', 'https://i.imgur.com/Ts53HNN.jpg'),
       ('picha79', 'Genadi', 'Genadiev', 'https://i.imgur.com/GsJb1jA.jpg');

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
       ('Animals'),
       ('Dogs'),
       ('Cats'),
       ('Cities'),
       ('Mountains'),
       ('Cars'),
       ('Beaches');


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

INSERT INTO images (title, story, image, user_id)
VALUES ('Leopard', 'Quick nap and back to hunting.', 'https://i.imgur.com/V1kXKir.jpg', 6),
       ('Serious kitty', 'As you can see my cat is a really good actor.', 'https://i.imgur.com/TH9NzDM.jpg', 7),
       ('Mops', 'I am so busy today, I have a full schedule - eat and taking naps every 3 hours.',
        'https://i.imgur.com/JWcaqIN.jpg', 8),
       ('Raccoon', 'Please god make America Great Again', 'https://i.imgur.com/LqEnBSP.jpg', 9),
       ('Mother lion', 'Come on little man you need to eat again', 'https://i.imgur.com/iIWwOsK.jpg', 10),
       ('Eagle', 'Looking sharp today, because of the men spice', 'https://i.imgur.com/ZhgtMUW.jpg', 11),
       ('Family', 'Animals seem to show us what a real family is really often', 'https://i.imgur.com/ZH1Q2PL.jpg', 12),
       ('Sunrise', 'Beautiful sunrise in the nature', 'https://i.imgur.com/rO6rIGD.jpg', 13),
       ('Lake view', 'Nature at its best', 'https://i.imgur.com/KRVNPVH.jpg', 14),
       ('Beach', 'My best friend and my favourite place in one picture', 'https://i.imgur.com/kHsYoe0.jpeg', 15),
       ('Snowy dog', 'Always happy when its snowing', 'https://i.imgur.com/BE4eDew.jpg', 16),
       ('Bears', 'Bears fighting', 'https://i.imgur.com/Ipfizk3.jpeg', 17),
       ('Horse', 'The national champion for 2021', 'https://i.imgur.com/vX84jNu.jpeg', 18),
       ('Wolf', 'Looking hungry', 'https://i.imgur.com/rHohnrq.jpeg', 19),
       ('nature', 'Very beautiful photo', 'https://i.imgur.com/4hQTAC9.jpg', 6),
       ('water & woods', ' I believe in God, only I spell it Nature.', 'https://i.imgur.com/hlp3JL7.jpg', 7),
       ('water and woods', ' Choose only one master—nature. ', 'https://i.imgur.com/9rCxSZB.jpg', 8),
       ('nature', 'Very beautiful photo', 'https://i.imgur.com/h08TAAp.jpg', 9),
       ('wooods', 'Leave the road, take the trails.', 'https://i.imgur.com/AsWbWxv.jpg', 10),
       ('rocks', 'Men argue. Nature acts.', 'https://i.imgur.com/KJsnPKs.jpg', 11),
       ('nature', 'Very beautiful photo', 'https://i.imgur.com/TBHphQn.jpg', 12),
       ('rocks & woods', ' Land really is the best art.', 'https://i.imgur.com/ojeGgMD.jpg', 13),
       ('nature', 'Spring is nature’s way of saying, ‘Let’s party!’ ', 'https://i.imgur.com/bfIlx8n.jpg', 14),
       ('Tree', 'Very beautiful photo', 'https://i.imgur.com/mkCliJj.jpg', 15),
       ('nature', 'The earth has music for those who listen.', 'https://i.imgur.com/0jRdy2h.jpg', 16),
       ('nature', 'The Amen of nature is always a flower.', 'https://i.imgur.com/6Ou2c1f.jpg', 17),
       ('nature', 'Very beautiful photo', 'https://i.imgur.com/Kc5a9vS.jpg', 18),
       ('wood', ' Adopt the pace of nature. Her secret is patience.', 'https://i.imgur.com/rQMJjXN.jpg', 19),
       ('nature', 'A weed is no more than a flower in disguise.', 'https://i.imgur.com/a1WJBud.jpg', 20),
       ('nature', 'Very beautiful photo', 'https://i.imgur.com/u3dmyUE.jpg', 21),
       ('nature', ' The earth is what we all have in common.', 'https://i.imgur.com/YfMw1Tu.jpg', 22),
       ('nature', 'Very beautiful photo', 'https://i.imgur.com/U76lqIW.jpg', 23),
       ('snow', 'The goal of life is living in agreement with nature.', 'https://i.imgur.com/u2jHxnp.jpg', 24),
       ('sunset', 'Best picture I have taken in 2020', 'https://i.imgur.com/CTWe6nU.jpg', 25),
       ('sunrise in Hawaii', 'Beautiful place to relax', 'https://i.imgur.com/KoguQkl.jpg', 26),
       ('laguna beach', 'Beautiful sunset in California', 'https://i.imgur.com/434p0Oy.jpg', 27),
       ('Zanzibar', 'Awesome day in Zanzibar', 'https://i.imgur.com/QbHS7UE.jpg', 28),
       ('Aston Martin', 'Photo-shoot with the new F1 safety car ', 'https://i.imgur.com/mDezdm2.jpg', 29),
       ('Mercedes S', 'The all new S class', 'https://i.imgur.com/QHl1vTl.jpg', 30),
       ('Ferrari', 'Photo-shoot for Top Gear', 'https://i.imgur.com/mIimSn2.jpg', 31),
       ('Mclaren', 'The all new Mclaren', 'https://i.imgur.com/jMm9mYr.jpg', 32),
       ('Red porsche', 'Cruising on the street', 'https://i.imgur.com/e4yV6SH.jpg', 33),
       ('Argentina mountain', 'The best view I have captured during my trip to Argentina', 'https://i.imgur.com/60k7Ius.jpg', 34),
       ('Mountain', 'Snowy day', 'https://i.imgur.com/6iGvbAM.jpg', 35),
       ('Fuji', 'Fuji mountain', 'https://i.imgur.com/vf3OUaZ.jpg', 37),
       ('Lake', 'Lake during winter', 'https://i.imgur.com/ZNcQdmH.jpg', 9),
       ('Stars', 'Stars in the sky', 'https://i.imgur.com/XhPA6O7.jpg', 36),
       ('Mountain', 'Snowy mountain', 'https://i.imgur.com/Z3o4ut5.jpg', 10);


INSERT INTO users_images(user_id, image_id)
VALUES (6, 1),
       (7, 2),
       (8, 3),
       (9, 4),
       (10, 5),
       (11, 6),
       (12, 7),
       (13, 8),
       (14, 9),
       (15, 10),
       (16, 11),
       (17, 12),
       (18, 13),
       (19, 14),
       (6, 15),
       (7, 16),
       (8, 17),
       (9, 18),
       (10, 19),
       (11, 20),
       (12, 21),
       (13, 22),
       (14, 23),
       (15, 24),
       (16, 25),
       (17, 26),
       (18, 27),
       (19, 28),
       (20, 29),
       (21, 30),
       (22, 31),
       (23, 32),
       (24, 33),
       (25, 34),
       (26, 35),
       (27, 36),
       (28, 37),
       (29, 38),
       (30, 39),
       (31, 40),
       (32, 41),
       (33, 42),
       (34, 43),
       (35, 44),
       (36, 45),
       (37, 46),
       (9, 47),
       (12, 48);


INSERT INTO contest(title, category_id, first_phase, second_phase, user_id, type_id, image_url, phase_id)
VALUES ('Animals', 2, '2021-04-16 18:52:35', '2021-04-17 18:52:35', 1, 1, 'https://i.imgur.com/yXDhZke.jpg', 2),
       ('Cars', 7, '2021-04-27 18:52:35', '2021-04-28 18:52:35', 2, 1, 'https://i.imgur.com/vJjekqs.jpg', 1),
       ('Mountains', 6, '2021-04-17 18:52:35', '2021-04-23 18:52:35', 3, 1, 'https://i.imgur.com/xnWz7Bl.jpg', 1),
       ('Lamborghini', 7, '2021-04-23 18:52:35', '2021-04-24 18:52:35', 4, 1, 'https://i.imgur.com/VRI2dVR.jpg', 1),
       ('Beach Photos', 8, '2021-04-17 18:52:35', '2021-04-30 18:52:35', 4, 2, 'https://i.imgur.com/1HMT838.jpg', 2);
INSERT
INTO user_points(user_id, points_id)
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

INSERT INTO contest_image(contest_id, image_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (1, 5),
       (1, 6),
       (2, 38),
       (2, 39),
       (2, 40),
       (2, 41),
       (2, 42),
       (3, 43),
       (3, 44),
       (3, 45),
       (3, 46),
       (3, 47),
       (3, 48),
       (5, 34),
       (5, 35),
       (5, 36),
       (5, 37),
       (5,33);

INSERT INTO contest_winner_images(contest_id, image_id)
VALUES (1, 1),
       (1, 2),
       (1, 5);

INSERT INTO image_reviews(user_id, contest_id, image_id, points, comment)
VALUES (1, 1, 1, 10, 'Good photo'),
       (1, 1, 2, 7, 'Good photo mate'),
       (1, 1, 5, 6, 'Very good'),
       (2, 1, 1, 10, 'Very well'),
       (2, 1, 2, 7, 'Good'),
       (2, 1, 5, 6, 'Good photo!');

INSERT INTO contest_participants(contest_id, user_id)
VALUES (1, 6),
       (1, 7),
       (1, 8),
       (1, 9),
       (1, 10),
       (1, 11),
       (1, 12),
       (1, 13),
       (2, 34),
       (2, 35),
       (2, 36),
       (2, 37),
       (2, 9),
       (2, 7),
       (5, 25),
       (5, 26),
       (5, 27),
       (5, 28),
       (5, 29);

INSERT INTO contest_jury(contest_id, user_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (2, 1),
       (2, 2),
       (2, 3),
       (2, 4),
       (3, 1),
       (3, 2),
       (3, 3),
       (3, 4),
       (4, 1),
       (4, 2),
       (4, 3),
       (4, 4),
       (5, 1),
       (5, 2),
       (5, 3),
       (5, 4);