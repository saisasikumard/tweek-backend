package com.taskManagment.tweek.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level=AccessLevel.PRIVATE)
@Table(name = "task")
public class Task {
    @Id
    @Column(name = "taskId",unique = true)
    String taskId;
    @Column(name = "title")
    String title;
    @Column(name = "description")
    String description;
    @Column(name = "status")
    String status;
    @Column(name = "priority")
    String priority;
    @Column(name = "dueDate")
    LocalDate dueDate;
    @Column(name = "userName",unique = true)
    //Timestamp createdTime;
    String userName;
}
