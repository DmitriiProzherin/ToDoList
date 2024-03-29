package org.behemothdi.todolist.entity;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "task", schema = "todo")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated
    @Column
    private Status status;
    @Column
    private String description;
}
