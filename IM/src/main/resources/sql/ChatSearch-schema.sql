
    create table t_chat_search (
        id bigint not null auto_increment,
        chat_id bigint not null,
        created_time datetime(6) not null,
        message_gram varchar(255) not null,
        room_id bigint not null,
        primary key (id)
    ) engine=InnoDB;

    alter table t_chat_search 
       add constraint uk_roomId unique (room_id, message_gram, chat_id);
