
    create table t_official_channel (
        id bigint not null auto_increment,
        created_time datetime(6) not null,
        is_public bit not null,
        name varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;

    alter table t_official_channel 
       add constraint uk_name unique (name);
