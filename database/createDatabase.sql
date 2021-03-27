DROP DATABASE IF EXISTS `photo_contest`;
CREATE DATABASE  IF NOT EXISTS `photo_contest`;
USE `photo_contest`;

create or replace table category
(
    category_id int auto_increment
        primary key,
    name        varchar(30) not null
);

create or replace table contest
(
    contest_id  int auto_increment
        primary key,
    title       varchar(50) not null,
    category_id int         not null,
    phase_one   date        not null,
    phase_two   time        not null,
    constraint contest_category_fk
        foreign key (category_id) references category (category_id)
);

create or replace table users
(
    user_id    int auto_increment
        primary key,
    user_name  varchar(20) not null,
    email      varchar(50) not null,
    first_name varchar(20) not null,
    last_name  varchar(20) not null,
    password   varchar(30) not null,
    role       varchar(50) not null,
    points     int         not null,
    constraint users_email_uindex
        unique (email),
    constraint users_user_name_uindex
        unique (user_name)
);

create or replace table images
(
    image_id int auto_increment
        primary key,
    URL      varchar(300) not null,
    user_id  int          not null,
    points   int          not null,
    constraint images_users_fk
        foreign key (user_id) references users (user_id)
);

create or replace table contest_photos
(
    contest_id int not null,
    image_id   int null,
    constraint contest_photos_contests_fk
        foreign key (contest_id) references contest (contest_id),
    constraint contest_photos_images_fk
        foreign key (image_id) references images (image_id)
);

