--liquibase formatted sql
--changeset Maria Belikova:1
create table cocktail_product
(
    cocktail_id        uuid not null
        constraint fk_cocktail_product_cocktail
            references cocktails(id)
            on delete cascade,
    product_id          uuid not null
        constraint fk_cocktail_product_product
            references products(id)
            on delete cascade,
    amount              double precision default 0,
    primary key         (product_id, cocktail_id)
);
