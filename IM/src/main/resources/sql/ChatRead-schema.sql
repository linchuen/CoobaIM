
    create table t_chat_read (
        id bigint not null auto_increment,
        chat_id bigint not null,
        created_time datetime(6) not null,
        room_id bigint not null,
        user_id bigint not null,
        primary key (id)
    ) engine=InnoDB;

    create index idx_read_roomId 
       on t_chat_read (room_id, user_id);
