create table IF NOT EXISTS product (
`id` bigint not null,
created_by varchar(255),
created_date datetime,
last_modified_by varchar(255),
last_modified_date datetime,
category varchar(255),
`desc` longtext,
name varchar(255) not null,
unit_price float not null,
`version` bigint not null,
primary key (id),
UNIQUE KEY `UK_opnl18c8phwl8gk0f7mao625d` (`name`)
);

CREATE TABLE  IF NOT EXISTS product_seq (
next_val BIGINT(20) NULL DEFAULT NULL
); 