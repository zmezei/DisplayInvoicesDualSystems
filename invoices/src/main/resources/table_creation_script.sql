create table invoice (id number(19), customer_name varchar(255) not null, issue_date timestamp not null, due_date timestamp not null, invoice_comment varchar(255), invoice_total float(19) not null, primary key(id));
create table invoice_item (id number(19), product_name varchar(255) not null, unit_price float(19) not null, quantity number(19) not null, total_item_price float(19) not null, invoice_id number(19) not null, primary key(id), foreign key(invoice_id) references invoice(id));

insert into invoice values (1, 'Customer1', current_timestamp, current_timestamp, 'Comment', 12314.1);
insert into invoice_item values (1, 'Product1', 1230.1, 2, 2460.2, 1);