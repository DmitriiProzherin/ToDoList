package org.behemothdi.todolist.entity;

import lombok.Data;
import org.behemothdi.todolist.entity.Status;
@Data
public class TaskInfo {
    private String description;
    private Status status;
}
