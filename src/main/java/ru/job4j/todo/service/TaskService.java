package ru.job4j.todo.service;

import ru.job4j.todo.model.Task;

import java.util.Collection;
import java.util.Optional;

public interface TaskService {

    Task save(Task task);

    Optional<Task> findById(int id);

    boolean deleteById(int id);

    boolean update(Task task);

    boolean updateDoneParameter(int id);

    Collection<Task> findAll();

    Collection<Task> findDoneTasks();

    Collection<Task> findNewTasks();

    Collection<Task> findOldNotDoneTasks();
}
