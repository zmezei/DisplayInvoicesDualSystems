create table invoice (id number(19), customer_name varchar(255) not null, issue_date timestamp not null, due_date timestamp not null, invoice_comment varchar(255), invoice_total decimal(9,2) not null, primary key(id));
create table invoice_item (id number(19), product_name varchar(255) not null, unit_price decimal(9,2) not null, quantity decimal(9,2) not null, total_item_price decimal(9,2) not null, invoice_id number(19) not null, primary key(id), foreign key(invoice_id) references invoice(id));

insert into invoice values (1, 'Customer1', current_timestamp, current_timestamp + interval '10' day, 'Comment1', 0);
insert into invoice values (2, 'Customer2', current_timestamp - interval '10' day, current_timestamp + interval '4' day, 'Comment2', 0);
insert into invoice values (3, 'Customer3', current_timestamp - interval '1' year, current_timestamp + interval '1' year, 'Comment3', 0);
insert into invoice values (4, 'Customer4', current_timestamp - interval '2' month, current_timestamp + interval '10' day, 'Comment4', 0);
insert into invoice values (5, 'Customer5', current_timestamp - interval '24' day, current_timestamp + interval '9' month, 'Comment5', 0);
insert into invoice values (6, 'Customer6', current_timestamp - interval '5' day, current_timestamp + interval '50' day, 'Comment6', 0);
insert into invoice values (7, 'Customer6', current_timestamp, current_timestamp + interval '35' day, 'Comment7', 0);

insert into invoice_item values (1, 'Product1', 1000, 2.5, 0, 1);
insert into invoice_item values (2, 'Product2', 2000, 30, 0, 1);
insert into invoice_item values (3, 'Product3', 500, 8, 0, 1);
insert into invoice_item values (4, 'Product4', 500, 234, 0, 2);
insert into invoice_item values (5, 'Product5', 1231, 23.4, 0, 2);
insert into invoice_item values (6, 'Product5', 100, 74, 0, 3);
insert into invoice_item values (7, 'Product1', 65000, 13, 0, 3);
insert into invoice_item values (8, 'Product6', 98, 98.23, 0, 3);
insert into invoice_item values (9, 'Product7', 345, 1, 0, 3);
insert into invoice_item values (10, 'Product3', 210, 2, 0, 4);
insert into invoice_item values (11, 'Product7', 345, 1234, 0, 5);
insert into invoice_item values (12, 'Product8', 98, 98.25, 0, 6);
insert into invoice_item values (13, 'Product2', 3425, 123, 0, 6);
insert into invoice_item values (14, 'Product4', 4, 543, 0, 7);
insert into invoice_item values (15, 'Product9', 45, 1.1, 0, 7);
insert into invoice_item values (16, 'Product2', 10, 432, 0, 7);

update invoice_item set total_item_price = quantity * unit_price;

update invoice i set invoice_total = (select sum(total_item_price) from invoice_item it where it.invoice_id = i.id);

commit work;
