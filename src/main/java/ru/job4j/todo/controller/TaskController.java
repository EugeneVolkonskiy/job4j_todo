package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.TaskService;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/tasks")
@AllArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/list")
    public String getAll(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        return "tasks/list";
    }

    @GetMapping("/create")
    public String getCreationPage() {
        return "tasks/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Task task, HttpSession session) {
        task.setUser((User) session.getAttribute("user"));
        taskService.save(task);
        return "redirect:/tasks/list";
    }

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable int id) {
        Optional<Task> task = taskService.findById(id);
        if (task.isEmpty()) {
            model.addAttribute("message", "Задача с указанным id не найдена");
            return "errors/fail";
        }
        model.addAttribute("task", task.get());
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
        return "tasks/update";
    }

    @PostMapping("/update")
    public String updateTask(@ModelAttribute Task task, Model model) {
        if (!taskService.update(task)) {
            model.addAttribute("message", "Не удалось изменить задачу");
            return "errors/fail";
        }
        return "redirect:/tasks/list";
    }

    @GetMapping("/done")
    public String getDoneTasks(Model model) {
        model.addAttribute("tasks", taskService.findDoneTasks());
        return "tasks/list";
    }

    @GetMapping("/new")
    public String getNewTasks(Model model) {
        model.addAttribute("tasks", taskService.findNewTasks());
        return "tasks/list";
    }

    @GetMapping("/old_not_done")
    public String getOldNotDoneTasks(Model model) {
        model.addAttribute("tasks", taskService.findOldNotDoneTasks());
        return "tasks/list";
    }
}
