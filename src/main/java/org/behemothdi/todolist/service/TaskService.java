package org.behemothdi.todolist.service;

import jakarta.transaction.Transactional;
import org.behemothdi.todolist.dao.TaskDAO;
import org.behemothdi.todolist.entity.Status;
import org.behemothdi.todolist.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskDAO taskDAO;

    public TaskService(@Autowired TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }

    public List<Task> getAll(int offset, int limit){
        return taskDAO.getAll(offset, limit);
    }

    public int getAllCount(){
        return taskDAO.getAllCount();
    }

    @Transactional
    public Task edit(int id, String description, Status status){
        Task task = taskDAO.getById(id);
        if (task == null) {
            // Лог эксепшена
            throw new RuntimeException("Not found");
        }

        task.setDescription(description);
        task.setStatus(status);
        taskDAO.updateOrCreate(task);

        return task;
    }

    public Task create(String description, Status status){
        Task task = new Task();

        task.setDescription(description);
        task.setStatus(status);

        taskDAO.updateOrCreate(task);

        return task;
    }

    @Transactional
    public void delete(int id) {
        Task task = taskDAO.getById(id);
        if (task == null) {
            // Лог эксепшена
            throw new RuntimeException("Not found");
        }

        taskDAO.delete(task);
    }
}
