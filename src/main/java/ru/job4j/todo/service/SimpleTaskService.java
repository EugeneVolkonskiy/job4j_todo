package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.repository.TaskRepository;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleTaskService implements TaskService {

    private TaskRepository taskRepository;

    @Override
    public Task save(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Optional<Task> findById(int id) {
        return taskRepository.findById(id);
    }

    @Override
    public boolean deleteById(int id) {
        return taskRepository.deleteById(id);
    }

    @Override
    public boolean update(Task task) {
        return taskRepository.update(task);
    }

    @Override
    public boolean updateDoneParameter(int id) {
        return taskRepository.updateDoneParameter(id);
    }

    @Override
    public Collection<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public Collection<Task> findDoneTasks() {
        return taskRepository.findDoneTasks();
    }

    @Override
    public Collection<Task> findNewTasks() {
        return taskRepository.findNewTasks();
    }

    @Override
    public Collection<Task> findOldNotDoneTasks() {
        return taskRepository.findOldNotDoneTasks();
    }

    @Override
    public Collection<Task> getTasksWithTimeZone(Collection<Task> tasks, User user) {
        for (Task task : tasks) {
            ZonedDateTime taskCreated = task.getCreated().atZone(ZoneId.of("UTC"));
            task.setCreated(taskCreated.withZoneSameInstant(ZoneId.of(user.getTimezone())).toLocalDateTime());
        }
        return tasks;
    }

    @Override
    public Task getTaskWithTimeZone(Task task, User user) {
            ZonedDateTime taskCreated = task.getCreated().atZone(ZoneId.of("UTC"));
            task.setCreated(taskCreated.withZoneSameInstant(ZoneId.of(user.getTimezone())).toLocalDateTime());
        return task;
    }
}


