--liquibase formatted sql
--changeset Maria Belikova:1
create table organizations
(
    id          uuid not null
                    constraint pk_organization
                        primary key,
    name        varchar(255),
    nickname    varchar(255),
    secret_code varchar(7)
);

--liquibase formatted sql
--changeset Maria Belikova:2
alter table organizations
add constraint organisation_nickname_unique unique (nickname);
