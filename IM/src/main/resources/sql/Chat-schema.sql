
    create table t_chat (
        id bigint not null auto_increment,
        created_time datetime(6) not null,
        message varchar(255) not null,
        name varchar(255) not null,
        room_id bigint not null,
        type tinyint not null,
        url varchar(255),
        user_id bigint not null,
        version integer,
        primary key (id)
    ) engine=InnoDB;

    create index idx_roomId 
       on t_chat (room_id, user_id);
