package org.behemothdi.todolist.controller;

import org.behemothdi.todolist.entity.Task;
import org.behemothdi.todolist.entity.TaskInfo;
import org.behemothdi.todolist.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping ("/")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/")
    public String display(
            Model model,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit
    )
    {
        List<Task> tasks = taskService.getAll((page - 1)*limit, limit);
        model.addAttribute("tasks", tasks);
        return "tasks";
    }


    @PostMapping("/{id}")
    public String edit(
            Model model,
            @PathVariable Integer id,
            @RequestBody TaskInfo info
    ){
        if (id == null || id < 0) {
            // Логгирование
            throw new RuntimeException("Invalid id");
        }

        Task task = taskService.edit(
                id,
                info.getDescription(),
                info.getStatus());

        return display(model, 1, 10);
    }


    @PostMapping("/")
    public String add(
            Model model,
            @RequestBody TaskInfo info
    ){
        taskService.create(
                info.getDescription(),
                info.getStatus());
        return display(model, 1, 10);
    }

    @DeleteMapping("/{id}")
    public String delete(
            Model model,
            @PathVariable Integer id
    ){
        if (id == null || id < 0) {
            // Логгирование
            throw new RuntimeException("Invalid id");
        }

        taskService.delete(id);

        return display(model, 1, 10);
    }
}
