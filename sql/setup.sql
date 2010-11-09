create role xactiontest password 'xactiontest' login;

create database customer owner xactiontest;
\c customer
create table customer (id serial primary key, name varchar);
alter table customer owner to xactiontest;
alter table customer add column version integer default(0);

create database orders owner xactiontest;
\c orders
create table orders (id serial primary key, date date, quantity integer);
alter table orders owner to xactiontest;
alter table orders add column version integer default(0);
