
    create table t_user_authority (
        id bigint not null auto_increment,
        authority varchar(255) not null,
        user_id bigint not null,
        primary key (id)
    ) engine=InnoDB;

    alter table t_user_authority 
       add constraint uk unique (user_id, authority);
