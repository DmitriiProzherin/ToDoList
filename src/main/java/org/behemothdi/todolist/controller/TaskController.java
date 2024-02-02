package org.behemothdi.todolist.controller;

import org.behemothdi.todolist.entity.Task;
import org.behemothdi.todolist.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping ("/")
public class TaskController {
    private final TaskService taskService;

    public TaskController(@Autowired TaskService taskService) {
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
    public void edit(
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

    }


    @PostMapping("/")
    public void add(
            Model model,
            @RequestBody TaskInfo info
    ){
        taskService.create(
                info.getDescription(),
                info.getStatus());
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

        return "tasks";
    }
}
