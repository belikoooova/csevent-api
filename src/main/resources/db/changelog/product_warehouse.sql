--liquibase formatted sql
--changeset Maria Belikova:1
create table product_warehouse
(
    product_id          uuid not null
                            constraint fk_product_warehouse_product
                                references products(id)
                                on delete cascade,
    warehouse_id        uuid not null
                            constraint fk_product_warehouse_warehouse
                                references warehouses(id)
                                on delete cascade,
    amount              double precision,
    primary key         (product_id, warehouse_id)
);