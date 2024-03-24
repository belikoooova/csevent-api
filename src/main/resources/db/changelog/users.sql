--liquibase formatted sql
--changeset Maria Belikova:1
create table users
(
    id          uuid not null
                constraint pk_user
                    primary key,
    email       varchar(255),
    password    varchar(255),
    name        varchar(255),
    photo_url   text
);

--liquibase formatted sql
--changeset Maria Belikova:2
alter table users
rename column photo_url to color;
