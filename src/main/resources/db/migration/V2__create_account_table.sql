create table account
(
    id             varchar(36) primary key,
    account_number varchar(12) unique,
    client_id      varchar(10),
    balance        int,
    is_blocked     boolean
);

insert into account (id, account_number, client_id, balance, is_blocked)
values ('26c6b9cb-0905-4715-9ac9-47851da81b5a', '000000000001', '1000000001', 250, false),
       ('655bf786-b4ed-4184-a093-2ac694638d6c', '000000000002', '1000000002', 100, false);