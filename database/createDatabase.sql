DROP
DATABASE IF EXISTS `photo-contest`;
CREATE
DATABASE IF NOT EXISTS `photo-contest`;
USE `photo-contest`;
create or replace table `photo-contest`.category
(
    category_id int auto_increment
        primary key,
    name        varchar(30) not null
);

create or replace table `photo-contest`.contest_phase
(
    phase_id int auto_increment
        primary key,
    name     varchar(30) not null
);

create or replace table `photo-contest`.contest_type
(
    type_id int auto_increment
        primary key,
    name    varchar(20) not null,
    constraint contest_type_name_uindex
        unique (name)
);

create or replace table `photo-contest`.mailbox
(
    message_id int auto_increment
        primary key,
    message    longtext   null,
    is_seen    tinyint(1) null
);

create or replace table `photo-contest`.points
(
    points_id int auto_increment
        primary key,
    points    int not null
);

create or replace table `photo-contest`.roles
(
    role_id int auto_increment
        primary key,
    name    varchar(30) not null
);

create or replace table `photo-contest`.user_credentials
(
    user_name varchar(30) not null,
    password  varchar(30) not null,
    constraint users_user_name_uindex
        unique (user_name)
);

alter table `photo-contest`.user_credentials
    add primary key (user_name);

create or replace table `photo-contest`.users
(
    user_id          int auto_increment
        primary key,
    user_credentials varchar(30)                                    not null,
    first_name       varchar(20)                                    not null,
    last_name        varchar(20)                                    not null,
    profileImage     text default 'https://i.imgur.com/GdDsxXO.png' not null,
    constraint users_user_credentials_fk
        foreign key (user_credentials) references `photo-contest`.user_credentials (user_name)
);

create or replace table `photo-contest`.contest
(
    contest_id         int auto_increment
        primary key,
    title              varchar(50)          not null,
    category_id        int                  not null,
    first_phase        datetime             not null,
    second_phase       datetime             not null,
    user_id            int                  not null,
    type_id            int                  not null,
    image_url          text                 not null,
    phase_id           int                  not null,
    is_jury            tinyint(1) default 0 not null,
    is_participant     tinyint(1) default 0 not null,
    has_image_uploaded tinyint(1) default 0 not null,
    constraint contest_category_fk
        foreign key (category_id) references `photo-contest`.category (category_id),
    constraint contest_organizers_fk
        foreign key (user_id) references `photo-contest`.users (user_id),
    constraint contest_phase_fk
        foreign key (phase_id) references `photo-contest`.contest_phase (phase_id),
    constraint contest_type_fk
        foreign key (type_id) references `photo-contest`.contest_type (type_id)
);

create or replace table `photo-contest`.contest_participants
(
    contest_id int not null,
    user_id    int not null,
    constraint contest_users_contest_fk
        foreign key (contest_id) references `photo-contest`.contest (contest_id),
    constraint contest_users_users_fk
        foreign key (user_id) references `photo-contest`.users (user_id)
);

create or replace table `photo-contest`.images
(
    image_id int auto_increment
        primary key,
    title    varchar(50) not null,
    story    text        not null,
    image    text        not null,
    user_id  int         not null,
    constraint images_users_fk
        foreign key (user_id) references `photo-contest`.users (user_id)
);

create or replace table `photo-contest`.contest_image
(
    contest_id int not null,
    image_id   int null,
    constraint contest_photos_contests_fk
        foreign key (contest_id) references `photo-contest`.contest (contest_id),
    constraint contest_photos_images_fk
        foreign key (image_id) references `photo-contest`.images (image_id)
);

create or replace table `photo-contest`.image_reviews
(
    image_review_id int auto_increment
        primary key,
    user_id         int      not null,
    contest_id      int      not null,
    image_id        int      not null,
    points          int      not null,
    comment         longtext not null,
    constraint image_reviews_contest_fk
        foreign key (contest_id) references `photo-contest`.contest (contest_id),
    constraint image_reviews_images_fk
        foreign key (image_id) references `photo-contest`.images (image_id),
    constraint image_reviews_user_credentials_fk
        foreign key (user_id) references `photo-contest`.users (user_id)
);

create or replace table `photo-contest`.jury_users
(
    contest_id int not null,
    user_id    int not null,
    constraint jury_users_contest_fk
        foreign key (contest_id) references `photo-contest`.contest (contest_id),
    constraint jury_users_users_fk
        foreign key (user_id) references `photo-contest`.users (user_id)
);

create or replace table `photo-contest`.user_points
(
    user_id   int not null,
    points_id int not null,
    constraint user_points_points_fk
        foreign key (points_id) references `photo-contest`.points (points_id),
    constraint user_points_users_fk
        foreign key (user_id) references `photo-contest`.users (user_id)
);

create or replace table `photo-contest`.users_images
(
    user_id  int not null,
    image_id int not null,
    constraint users_images_images_fk
        foreign key (image_id) references `photo-contest`.images (image_id),
    constraint users_images_user_credentials_fk
        foreign key (user_id) references `photo-contest`.users (user_id)
);

create or replace table `photo-contest`.users_mails
(
    user_id    int not null,
    message_id int not null,
    constraint table_name_mailbox_fk
        foreign key (message_id) references `photo-contest`.mailbox (message_id),
    constraint table_name_users_fk
        foreign key (user_id) references `photo-contest`.users (user_id)
);

create or replace table `photo-contest`.users_roles
(
    user_id int not null,
    role_id int not null,
    constraint users_roles_fk
        foreign key (role_id) references `photo-contest`.roles (role_id),
    constraint users_roles_user_credentials_fk
        foreign key (user_id) references `photo-contest`.users (user_id)
);



