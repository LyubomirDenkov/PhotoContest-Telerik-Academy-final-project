DROP DATABASE IF EXISTS `photo-contest`;
CREATE DATABASE IF NOT EXISTS `photo-contest`;
USE `photo-contest`;
create or replace table category
(
    category_id int auto_increment
        primary key,
    name        varchar(30) not null
);

create or replace table contest_phase
(
    phase_id int auto_increment
        primary key,
    name     varchar(30) not null
);

create or replace table contest_type
(
    type_id int auto_increment
        primary key,
    name    varchar(20) not null
);


create or replace table points
(
    points_id int auto_increment
        primary key,
    points    int not null
);

create or replace table roles
(
    role_id int auto_increment
        primary key,
    name    varchar(30) not null
);

create or replace table user_credentials
(
    user_name varchar(30) not null,
    password  varchar(30) not null,
    constraint users_user_name_uindex
        unique (user_name)
);

alter table user_credentials
    add primary key (user_name);

create or replace table users
(
    user_id          int auto_increment
        primary key,
    user_credentials varchar(30)                                    not null,
    first_name       varchar(20)                                    not null,
    last_name        varchar(20)                                    not null,
    profileImage     text default 'https://i.imgur.com/0fRs86a.jpg' null,
    constraint users_user_credentials_fk
        foreign key (user_credentials) references user_credentials (user_name)
);

create or replace table images
(
    image_id int auto_increment
        primary key,
    title    varchar(50) not null,
    story    text        not null,
    image    text        not null,
    user_id int not null,
    points int not null,
        constraint images_users_fk
        foreign key (user_id) references users (user_id)

);
create or replace table contest
(
    contest_id      int auto_increment
        primary key,
    title           varchar(50)                           not null,
    category_id     int                                   not null,
    starting_date   timestamp default current_timestamp() not null on update current_timestamp(),
    phase1_days     int                                   not null,
    phase2_hours    int                                   not null,
    user_id         int                                   not null,
    type_id         int                                   not null,
    image_url       text                                  not null,
    phase_id        int                                   not null,
    isPointsAwarded tinyint(1)                            not null,
    constraint contest_category_fk
        foreign key (category_id) references category (category_id),
    constraint contest_organizers_fk
        foreign key (user_id) references users (user_id),
    constraint contest_phase_fk
        foreign key (phase_id) references contest_phase (phase_id),
    constraint contest_type_fk
        foreign key (type_id) references contest_type (type_id)
);

create or replace table contest_image
(
    contest_id int not null,
    image_id   int null,
    constraint contest_photos_contests_fk
        foreign key (contest_id) references contest (contest_id),
    constraint contest_photos_images_fk
        foreign key (image_id) references images (image_id)
);

create or replace table contest_participants
(
    contest_id int not null,
    user_id    int not null,
    constraint contest_users_contest_fk
        foreign key (contest_id) references contest (contest_id),
    constraint contest_users_users_fk
        foreign key (user_id) references users (user_id)
);

create or replace table jury_users
(
    contest_id int not null,
    user_id    int not null,
    constraint jury_users_contest_fk
        foreign key (contest_id) references contest (contest_id),
    constraint jury_users_users_fk
        foreign key (user_id) references users (user_id)
);

create or replace table rated_images
(
    rated_image_id int auto_increment
        primary key,
    user_id        int      not null,
    image_id       int      not null,
    points         int      not null,
    comment        longtext not null,
    constraint rated_images_images_fk
        foreign key (image_id) references images (image_id),
    constraint rated_images_user_credentials_fk
        foreign key (user_id) references users (user_id)
);

create or replace table user_points
(
    user_id   int not null,
    points_id int not null,
    constraint user_points_points_fk
        foreign key (points_id) references points (points_id),
    constraint user_points_users_fk
        foreign key (user_id) references users (user_id)
);

create or replace table users_images
(
    user_id  int not null,
    image_id int not null,
    constraint users_images_images_fk
        foreign key (image_id) references images (image_id),
    constraint users_images_user_credentials_fk
        foreign key (user_id) references users (user_id)
);

create or replace table users_roles
(
    user_id int not null,
    role_id int not null,
    constraint users_roles_fk
        foreign key (role_id) references roles (role_id),
    constraint users_roles_user_credentials_fk
        foreign key (user_id) references users (user_id)
);

