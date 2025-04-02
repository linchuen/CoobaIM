
    create table t_session (
        id bigint not null auto_increment,
        enable bit not null,
        expire_time datetime(6) not null,
        ip varchar(255),
        login_time datetime(6) not null,
        logout_time datetime(6),
        platform tinyint not null check (platform between 0 and 3),
        pre_token varchar(1000),
        token varchar(1000) not null,
        user_id bigint not null,
        primary key (id)
    ) engine=InnoDB;

    alter table t_session 
       add constraint uk_userId unique (user_id, platform);
