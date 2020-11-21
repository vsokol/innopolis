drop table if exists app_logs;

create table app_logs (
    log_id     varchar(100) primary key
  , entry_date timestamp
  , logger     varchar(100)
  , log_level  varchar(100)
  , message    text
  , exception  text
);
