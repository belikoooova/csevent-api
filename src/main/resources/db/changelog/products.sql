--liquibase formatted sql
--changeset Maria Belikova:1
create table products
(
    id                  uuid not null
                            constraint pk_product
                                primary key,
    name                varchar(255),
    unit                varchar(255),
    organization_id     uuid,
    tag                 varchar(255)
);