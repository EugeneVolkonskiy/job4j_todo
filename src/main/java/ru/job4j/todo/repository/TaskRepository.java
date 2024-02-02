package ru.job4j.todo.repository;

import ru.job4j.todo.model.Task;

import java.util.Collection;

public interface TaskRepository {

    Task save(Task task);

    Task findById(int id);

    void deleteById(int id);

    void update(Task task);

    void updateDoneParameter(int id);

    Collection<Task> findAll();

    Collection<Task> findDoneTasks();

    Collection<Task> findNewTasks();
}
