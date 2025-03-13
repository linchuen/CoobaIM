
    create table t_user_detail (
        id bigint not null auto_increment,
        created_time datetime(6) not null,
        email varchar(255) not null,
        name varchar(255) not null,
        remark varchar(255),
        tags varchar(255),
        user_id bigint not null,
        primary key (id)
    ) engine=InnoDB;

    alter table t_user_detail 
       add constraint uk_user unique (user_id);
