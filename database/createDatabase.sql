DROP
DATABASE IF EXISTS `photo-contest`;
CREATE
DATABASE  IF NOT EXISTS `photo-contest`;
USE
`photo-contest`;

create
or replace table category
(
    category_id int auto_increment
        primary key,
    name        varchar(30) not null
);

create
or replace table contest
(
    contest_id  int auto_increment
        primary key,
    title       varchar(50) not null,
    category_id int         not null,
    phase_one   timestamp        not null,
    phase_two   timestamp        not null,
user_id int not null,
    constraint contest_category_fk
        foreign key (category_id) references category (category_id)

);

create
or replace table images
(
    image_id int auto_increment
        primary key,
    URL      varchar(300) not null,
    points   int          not null
);

create
or replace table contest_image
(
    contest_id int not null,
    image_id   int null,
    constraint contest_photos_contests_fk
        foreign key (contest_id) references contest (contest_id),
    constraint contest_photos_images_fk
        foreign key (image_id) references images (image_id)
);

create
or replace table ranks
(
    rank_id int auto_increment
        primary key,
    name    varchar(50) not null,
    constraint ranks_name_uindex
        unique (name)
);

create
or replace table roles
(
    role_id int auto_increment
        primary key,
    name    varchar(30) not null
);

create
or replace table users
(
    user_id    int auto_increment
        primary key,
    user_name  varchar(30) not null,
    email      varchar(50) not null,
    first_name varchar(20) not null,
    last_name  varchar(20) not null,
    password   varchar(30) not null,
    rank_id    int         not null,
    points     int         not null,
    constraint users_email_uindex
        unique (email),
    constraint users_user_name_uindex
        unique (user_name),
    constraint users_ranks_fk
        foreign key (rank_id) references ranks (rank_id)
);

create
or replace table contest_jury
(
    contest_id int not null,
    user_id    int not null,
    constraint contest_jury_contest_fk
        foreign key (contest_id) references contest (contest_id),
    constraint contest_jury_user_fk
        foreign key (user_id) references users (user_id)
);

create
or replace table contest_participants
(
    contest_id int not null,
    user_id    int not null,
    constraint contest_users_contest_fk
        foreign key (contest_id) references contest (contest_id),
    constraint contest_users_users_fk
        foreign key (user_id) references users (user_id)
);

create
or replace table users_images
(
    user_id  int not null,
    image_id int not null,
    constraint users_images__fk
        foreign key (user_id) references users (user_id),
    constraint users_images_images_fk
        foreign key (image_id) references images (image_id)
);

create
or replace table users_roles
(
    user_id int not null,
    role_id int not null,
    constraint users_roles__fk
        foreign key (role_id) references roles (role_id),
    constraint users_roles_users_fk
        foreign key (user_id) references users (user_id)
);

