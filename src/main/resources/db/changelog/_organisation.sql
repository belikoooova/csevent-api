--liquibase formatted sql
--changeset Maria Belikova:1
create table _organization
(
    id          uuid not null
                    constraint pk_organization
                        primary key,
    name        varchar(255),
    nickname    varchar(255),
    secret_code varchar(7)
);
