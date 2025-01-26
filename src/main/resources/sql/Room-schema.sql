
    create table room (
        id bigint not null auto_increment,
        created_time datetime(6) not null,
        name varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;
