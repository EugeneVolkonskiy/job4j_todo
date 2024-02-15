package ru.job4j.todo.service;

import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;

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

    Collection<Task> getTasksWithTimeZone(Collection<Task> tasks, User user);

    Task getTaskWithTimeZone(Task task, User user);
}
