
    create table t_room (
        id bigint not null auto_increment,
        created_time datetime(6) not null,
        name varchar(255) not null,
        room_type_enum tinyint not null check (room_type_enum between 0 and 1),
        primary key (id)
    ) engine=InnoDB;
