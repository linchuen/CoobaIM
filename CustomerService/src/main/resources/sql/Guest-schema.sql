
    create table t_guest (
        id bigint not null auto_increment,
        created_time datetime(6) not null,
        user_id bigint not null,
        primary key (id)
    ) engine=InnoDB;

    alter table t_guest 
       add constraint uk_user unique (user_id);
