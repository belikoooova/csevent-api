--liquibase formatted sql
--changeset Maria Belikova:1
create table event_product
(
    event_id            uuid not null
                            constraint fk_event_event_product
                                references events(id)
                                on delete cascade,
    product_id          uuid not null
                            constraint fk_product_event_product
                                references products(id)
                                on delete cascade,
    amount              double precision default 0,
    primary key         (product_id, event_id)
);

--changeset Maria Belikova:2
alter table event_product
add column is_purchased boolean default false;

--changeset Maria Belikova:3
alter table event_product
rename column amount to price;

--changeset Maria Belikova:4
alter table event_product
add column to_buy_amount double precision default 0;
