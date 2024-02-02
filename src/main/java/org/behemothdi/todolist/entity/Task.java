package org.behemothdi.todolist.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "task", schema = "todo")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated
    @Column(name = "status")
    private Status status;
    @Column(name = "description")
    private String description;
}
