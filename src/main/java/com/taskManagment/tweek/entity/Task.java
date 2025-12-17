package com.taskManagment.tweek.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

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
    String taskId;
    String title;
    String description;
    String status;
    String priority;
    LocalDate dueDate;
    String userName;
}
