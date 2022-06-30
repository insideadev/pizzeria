create table orders
(
    id         bigint not null auto_increment,
    customer   varchar(255),
    quantity   integer,
    status     enum('CREATED', 'READY', 'DELIVERED') default 'CREATED',
    created_at DATETIME default NOW(),
    updated_at DATETIME default NOW(),
    primary key (id)
) engine = InnoDB