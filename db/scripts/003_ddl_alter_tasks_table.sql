ALTER table tasks
ADD column user_id int references todo_users(id);