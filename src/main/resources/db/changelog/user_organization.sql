-- liquibase formatted sql
-- changeset Maria Belikova:1
create table user_organization
(
    user_id             uuid not null
                            constraint fk_user_organization_user
                                references users(id)
                                on delete cascade,
    organization_id     uuid not null
                            constraint fk_user_organization_organization
                                references organizations(id)
                                on delete cascade,
    role                varchar(255),
    primary key         (user_id, organization_id)
)