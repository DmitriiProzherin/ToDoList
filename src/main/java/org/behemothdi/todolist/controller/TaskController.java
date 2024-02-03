package org.behemothdi.todolist.controller;

import org.behemothdi.todolist.entity.Task;
import org.behemothdi.todolist.entity.TaskInfo;
import org.behemothdi.todolist.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        model.addAttribute("current_page", page);
        int totalPages = (int) Math.ceil(1.0*taskService.getAllCount() / limit);
        List<Integer> pageNumbers = IntStream.range(1, totalPages+1).boxed().toList();
        model.addAttribute("page_numbers", pageNumbers);
        return "tasks";
    }


    @PostMapping("/{id}")
    public String edit(
            Model model,
            @PathVariable(name = "id") Integer id,
            @RequestBody TaskInfo info
    ){
        if (id == null || id < 0) {
            // Логгирование
            throw new RuntimeException("Invalid id");
        }

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
            @PathVariable(name = "id") Integer id
    ){
        if (id == null || id < 0) {
            // Логгирование
            throw new RuntimeException("Invalid id");
        }

        taskService.delete(id);

        return display(model, 1, 10);
    }
}
