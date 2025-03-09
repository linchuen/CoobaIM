
    create table t_agent_customer (
        id bigint not null auto_increment,
        agent_user_id bigint not null,
        created_time datetime(6) not null,
        customer_user_id bigint not null,
        show_name varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;

    create index idx_customer 
       on t_agent_customer (customer_user_id);

    alter table t_agent_customer 
       add constraint uk_agent_user unique (agent_user_id);
