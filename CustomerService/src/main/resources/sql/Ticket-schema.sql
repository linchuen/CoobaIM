
    create table t_ticket (
        id bigint not null auto_increment,
        agent_user_id bigint not null,
        created_time datetime(6) not null,
        customer_user_id bigint not null,
        is_open bit not null,
        name varchar(255) not null,
        room_id bigint not null,
        primary key (id)
    ) engine=InnoDB;

    create index idx_customer 
       on t_ticket (created_time, customer_user_id);

    create index idx_agent 
       on t_ticket (agent_user_id, customer_user_id);

    alter table t_ticket 
       add constraint uk_room_name unique (`room_id, name`);
