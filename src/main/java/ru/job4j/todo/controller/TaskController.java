package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.CategoryService;
import ru.job4j.todo.service.PriorityService;
import ru.job4j.todo.service.TaskService;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/tasks")
@AllArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final PriorityService priorityService;
    private final CategoryService categoryService;

    @GetMapping("/list")
    public String getAll(Model model, @SessionAttribute User user) {
        Collection<Task> tasksWithTimeZone = taskService.getTasksWithTimeZone(taskService.findAll(), user);
        model.addAttribute("tasks", tasksWithTimeZone);
        return "tasks/list";
    }

    @GetMapping("/create")
    public String getCreationPage(Model model) {
        model.addAttribute("priorities", priorityService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        return "tasks/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Task task, @SessionAttribute User user, @RequestParam Set<Integer> categoriesId) {
        task.setUser((user));
        task.setCategories(categoryService.findById(categoriesId));
        taskService.save(task);
        return "redirect:/tasks/list";
    }

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable int id, @SessionAttribute User user) {
        Optional<Task> task = taskService.findById(id);
        if (task.isEmpty()) {
            model.addAttribute("message", "Задача с указанным id не найдена");
            return "errors/fail";
        }
        Task taskWithTimeZone = taskService.getTaskWithTimeZone(task.get(), user);
        model.addAttribute("task", taskWithTimeZone);
        return "tasks/one";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable int id, Model model) {
        if (!taskService.deleteById(id)) {
            model.addAttribute("message", "Задача с указанным id не найдена");
            return "errors/fail";
        }
        return "redirect:/tasks/list";
    }

    @GetMapping("/done/{id}")
    public String updateDone(@PathVariable int id, Model model) {
        if (!taskService.updateDoneParameter(id)) {
            model.addAttribute("message", "Не удалось изменить параметр done");
            return "errors/fail";
        }
        return "redirect:/tasks/list";
    }

    @GetMapping("/update/{id}")
    public String getTask(Model model, @PathVariable int id) {
        Optional<Task> task = taskService.findById(id);
        if (task.isEmpty()) {
            model.addAttribute("message", "Задача с указанным id не найдена");
            return "errors/fail";
        }
        model.addAttribute("task", task.get());
        model.addAttribute("priorities", priorityService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        return "tasks/update";
    }

    @PostMapping("/update")
    public String updateTask(@ModelAttribute Task task, Model model, @RequestParam Set<Integer> categoriesId, @SessionAttribute User user) {
        task.setUser(user);
        task.setCategories(categoryService.findById(categoriesId));
        if (!taskService.update(task)) {
            model.addAttribute("message", "Не удалось изменить задачу");
            return "errors/fail";
        }
        return "redirect:/tasks/list";
    }

    @GetMapping("/done")
    public String getDoneTasks(Model model, @SessionAttribute User user) {
        Collection<Task> doneTasksWithTimeZone = taskService.getTasksWithTimeZone(taskService.findDoneTasks(), user);
        model.addAttribute("tasks", doneTasksWithTimeZone);
        return "tasks/list";
    }

    @GetMapping("/new")
    public String getNewTasks(Model model, @SessionAttribute User user) {
        Collection<Task> newTasksWithTimeZone = taskService.getTasksWithTimeZone(taskService.findNewTasks(), user);
        model.addAttribute("tasks", newTasksWithTimeZone);
        return "tasks/list";
    }

    @GetMapping("/old_not_done")
    public String getOldNotDoneTasks(Model model, @SessionAttribute User user) {
        Collection<Task> oldNotDoneTasksWithTimeZone = taskService.getTasksWithTimeZone(taskService.findOldNotDoneTasks(), user);
        model.addAttribute("tasks", oldNotDoneTasksWithTimeZone);
        return "tasks/list";
    }
}
