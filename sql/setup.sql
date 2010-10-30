create role xactiontest password 'xactiontest' login;

create database t1 owner xactiontest;
\c t1
create table customer (id serial primary key, name varchar);
alter table customer owner to xactiontest;
alter table customer add column version integer default(0);

create database t2 owner xactiontest;
\c t2
create table orders (id serial primary key, date date, quantity integer);
alter table orders owner to xactiontest;
alter table orders add column version integer default(0);
