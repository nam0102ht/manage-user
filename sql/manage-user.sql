create table department
(
    id          int auto_increment
        primary key,
    name        varchar(255) charset utf8 null,
    description longtext                  null,
    personId    int                       not null
);

create table role
(
    id          int auto_increment
        primary key,
    name        varchar(255) charset utf8 null,
    description longtext                  null
);

create table teams
(
    id           int auto_increment
        primary key,
    name         varchar(255) charset utf8 null,
    departmentId int                       not null,
    constraint teams_department_id_fk
        foreign key (departmentId) references department (id)
);

create table person
(
    id          int auto_increment
        primary key,
    firstname   varchar(255) charset utf8 null,
    lastname    varchar(255) charset utf8 null,
    dob         bigint                    null,
    address     longtext                  null,
    email       varchar(255) charset utf8 null,
    phoneNumber varchar(255)              not null,
    extras      longtext                  null comment '""',
    roleId      int                       null,
    teamId      int                       null,
    isAdmin     int                       null,
    constraint person_teams_id_fk
        foreign key (teamId) references teams (id)
);

alter table department
    add constraint department_person_id_fk
        foreign key (personId) references person (id);

create index person_role_id_fk
    on person (roleId);

create table teams_persons
(
    id       int auto_increment
        primary key,
    personId int null,
    teamId   int null,
    constraint teams_persons_person_id_fk
        foreign key (personId) references person (id),
    constraint teams_persons_teams_id_fk
        foreign key (teamId) references teams (id)
);

