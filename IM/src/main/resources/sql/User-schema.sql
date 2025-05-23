
    create table t_user (
        id bigint not null auto_increment,
        avatar varchar(255) default '' not null,
        created_time datetime(6),
        email varchar(255) not null,
        name varchar(255) not null,
        partner varchar(255) default 'cooba' not null,
        password varchar(255) not null,
        role varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;

    alter table t_user 
       add constraint uk_email unique (email, partner);

    alter table t_user 
       add constraint uk_name unique (name, partner);
