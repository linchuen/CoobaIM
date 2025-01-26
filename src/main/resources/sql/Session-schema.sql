
    create table session (
        id bigint not null auto_increment,
        token varchar(255) not null,
        user_id bigint not null,
        primary key (id)
    ) engine=InnoDB;

    alter table session 
       add constraint `uk-userId` unique (user_id);
