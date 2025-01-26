
    create table notification (
        id bigint not null auto_increment,
        created_time datetime(6) not null,
        message varchar(255) not null,
        user_id bigint not null,
        primary key (id)
    ) engine=InnoDB;
