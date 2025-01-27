
    create table session (
        id bigint not null auto_increment,
        enable bit not null,
        expire_time datetime(6) not null,
        ip varchar(255),
        login_time datetime(6) not null,
        logout_time datetime(6),
        platform varchar(255),
        token varchar(1000) not null,
        user_id bigint not null,
        primary key (id)
    ) engine=InnoDB;

    alter table session 
       add constraint `uk-userId` unique (user_id);
