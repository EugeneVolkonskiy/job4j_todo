CREATE TABLE todo_users (
id       serial primary key,
email     text          not null,
login    text   UNIQUE not null,
password text          not null
);