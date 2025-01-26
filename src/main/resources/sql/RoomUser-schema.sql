
    create table room_user (
        id bigint not null auto_increment,
        created_time datetime(6) not null,
        room_id bigint not null,
        user_id bigint not null,
        primary key (id)
    ) engine=InnoDB;

    create index idx_userId 
       on room_user (user_id, room_id);

    alter table room_user 
       add constraint uk_roomId unique (room_id, user_id);
