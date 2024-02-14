package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Repository
@AllArgsConstructor
public class HbmCategoryRepository implements CategoryRepository {

    private final CrudRepository crudRepository;

    @Override
    public Collection<Category> findAll() {
        return crudRepository.query("FROM Category", Category.class);
    }

    @Override
    public Set<Category> findById(Set<Integer> categoriesId) {
        return new HashSet<>(crudRepository.query(
                "FROM Category WHERE id IN :fCategories", Category.class,
                Map.of("fCategories", categoriesId)
        ));
    }
}
