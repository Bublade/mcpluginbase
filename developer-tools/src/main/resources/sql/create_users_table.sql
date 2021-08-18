create table if not exists users (
    id bigint auto_increment not null,
    uuid varchar(36),
    note varchar(256),

    primary key(id),
    unique (uuid)
);
