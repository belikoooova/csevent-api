--liquibase formatted sql
--changeset Maria Belikova:1
create table _user
(
    id          uuid not null
                constraint pk_user
                    primary key,
    email       varchar(255),
    name        varchar(255),
    photo_url   text
);
