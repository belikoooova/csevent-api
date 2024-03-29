--liquibase formatted sql
--changeset Maria Belikova:1
create table events (
    id                  uuid not null
                            constraint pk_event
                                primary key,
    name                varchar(255),
    address             varchar(255),
    color               varchar(255),
    guests              integer,
    date_time           timestamp without time zone,
    organization_id     uuid
);

--changeset Maria Belikova:2
alter table events alter column guests set default 0;

--changeset Maria Belikova:3
alter table events
add column theme varchar(255) default '';
