
    create table t_friend (
        id bigint not null auto_increment,
        created_time datetime(6) not null,
        friend_user_id bigint not null,
        show_name varchar(255) not null,
        user_id bigint not null,
        primary key (id)
    ) engine=InnoDB;

    alter table t_friend 
       add constraint uk_userId unique (user_id, friend_user_id);
