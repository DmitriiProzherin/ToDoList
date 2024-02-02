package org.behemothdi.todolist.controller;

import lombok.Data;
import org.behemothdi.todolist.entity.Status;
@Data
public class TaskInfo {
    private String description;
    private Status status;
}
