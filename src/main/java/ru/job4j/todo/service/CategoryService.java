package ru.job4j.todo.service;

import ru.job4j.todo.model.Category;

import java.util.Collection;
import java.util.Set;

public interface CategoryService {

    Collection<Category> findAll();

    Set<Category> findById(Set<Integer> categoriesId);
}
