--liquibase formatted sql
--changeset Maria Belikova:1
create table cocktails (
    id                  uuid not null
                            constraint pk_cocktail
                                primary key,
    name                varchar(255),
    type                varchar(255),
    event_id            uuid
);
