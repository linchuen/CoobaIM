
    create table friend_apply (
        id bigint not null auto_increment,
        apply_user_id bigint not null,
        created_time datetime(6) not null,
        is_permit bit,
        permit_user_id bigint not null,
        primary key (id)
    ) engine=InnoDB;

    create index idx_apply 
       on friend_apply (apply_user_id);

    create index idx_permit 
       on friend_apply (permit_user_id);

    alter table friend_apply 
       add constraint uk_apply_permit unique (apply_user_id, permit_user_id);
