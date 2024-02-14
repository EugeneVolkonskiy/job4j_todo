package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HbmTaskRepository implements TaskRepository {

    private final CrudRepository crudRepository;

    @Override
    public Task save(Task task) {
        crudRepository.run(session -> session.save(task));
        return task;
    }

    @Override
    public Optional<Task> findById(int id) {
        return crudRepository.optional(
                "FROM Task t JOIN FETCH t.priority JOIN FETCH t.categories WHERE t.id = :fId", Task.class,
                Map.of("fId", id)
        );
    }

    @Override
    public boolean deleteById(int id) {
        return crudRepository.bool(
                "DELETE Task WHERE id = :fId",
                Map.of("fId", id));
    }

    @Override
    public boolean update(Task task) {
        try {
            crudRepository.run(session -> session.update(task));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateDoneParameter(int id) {
        return crudRepository.bool(
                "UPDATE Task SET done = true WHERE id = :fId",
                Map.of("fId", id)
        );
    }

    @Override
    public Collection<Task> findAll() {
        return crudRepository.query("SELECT DISTINCT t FROM Task t JOIN FETCH t.priority JOIN FETCH t.categories", Task.class);
    }

    @Override
    public Collection<Task> findDoneTasks() {
        return crudRepository.query("SELECT DISTINCT t FROM Task t "
                + "JOIN FETCH t.priority JOIN FETCH t.categories WHERE done = true", Task.class);
    }

    @Override
    public Collection<Task> findNewTasks() {
        return crudRepository.query(
                "SELECT DISTINCT t FROM Task t "
                        + "JOIN FETCH t.priority JOIN FETCH t.categories WHERE created > :fDate", Task.class,
                Map.of("fDate", LocalDateTime.now().minusDays(1))
        );
    }

    @Override
    public Collection<Task> findOldNotDoneTasks() {
        return crudRepository.query(
                "SELECT DISTINCT t FROM Task t JOIN FETCH t.priority"
                        + " JOIN FETCH t.categories WHERE created < :fDate AND done = false", Task.class,
                Map.of("fDate", LocalDateTime.now().minusDays(1))
        );
    }
}
