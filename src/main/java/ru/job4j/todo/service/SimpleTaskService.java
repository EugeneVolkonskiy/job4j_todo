package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.repository.TaskRepository;

import java.util.Collection;

@Service
@AllArgsConstructor
public class SimpleTaskService implements TaskService {

    private TaskRepository taskRepository;

    @Override
    public Task save(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Task findById(int id) {
        return taskRepository.findById(id);
    }

    @Override
    public void deleteById(int id) {
        taskRepository.deleteById(id);
    }

    @Override
    public void update(Task task) {
        taskRepository.update(task);
    }

    @Override
    public void updateDoneParameter(int id) {
        taskRepository.updateDoneParameter(id);
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
}
