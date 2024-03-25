--liquibase formatted sql
--changeset Maria Belikova:1
create table user_event
(
    user_id          uuid not null
        constraint fk_user_event_user
            references users(id)
            on delete cascade,
    event_id        uuid not null
        constraint fk_user_event_event
            references events(id)
            on delete cascade,
    primary key         (user_id, event_id)
);
