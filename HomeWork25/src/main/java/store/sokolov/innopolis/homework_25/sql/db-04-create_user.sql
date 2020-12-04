-- создание таблиц пользователей
create table if not exists users (
    id        bigserial
  , login     varchar(15) not null
  , password  varchar(50)
  , name      varchar(50) not null
  , is_lock   boolean     not null default true
  , full_name text
);

alter table if exists users add constraint users_id_pk primary key (id);

alter table if exists users add constraint users_login_uk unique (login);

alter sequence if exists users_id_seq restart with 10000;
