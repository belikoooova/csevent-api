--liquibase formatted sql
--changeset Maria Belikova:1
create table warehouses
(
    id                  uuid not null
                            constraint pk_warehouse
                                primary key,
    name                varchar(255),
    address             varchar(255),
    organization_id     uuid
);

--changeset Maria Belikova:2
alter table warehouses
alter column name
set default '';

alter table warehouses
alter column address
set default '';
