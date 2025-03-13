
    create table t_agent (
        id bigint not null auto_increment,
        created_time datetime(6) not null,
        department varchar(255) not null,
        is_default bit not null,
        is_disable bit not null,
        name varchar(255) not null,
        updated_time datetime(6) not null,
        user_id bigint not null,
        primary key (id)
    ) engine=InnoDB;

    alter table t_agent 
       add constraint uk_user unique (user_id);
