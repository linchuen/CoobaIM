
    create table t_chat (
        id bigint not null auto_increment,
        created_time datetime(6) not null,
        message varchar(255) not null,
        name varchar(255) not null,
        room_id bigint not null,
        type tinyint not null check (type between 0 and 3),
        url varchar(1000),
        user_id bigint not null,
        uuid varchar(100) not null,
        version integer,
        primary key (id)
    ) engine=InnoDB;

    create index idx_roomId 
       on t_chat (room_id, user_id);
