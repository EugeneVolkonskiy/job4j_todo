create table tasks_categories
(
id serial primary key,
task_id int references tasks(id) not null,
category_id int references categories(id) not null,
unique(task_id, category_id)
);