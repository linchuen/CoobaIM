
    create table t_room_user (
        id bigint not null auto_increment,
        created_time datetime(6) not null,
        room_id bigint not null,
        room_role_enum tinyint not null,
        show_name varchar(255),
        user_id bigint not null,
        primary key (id)
    ) engine=InnoDB;

    create index idx_userId 
       on t_room_user (user_id, room_id);

    alter table t_room_user 
       add constraint uk_roomId unique (room_id, user_id);
