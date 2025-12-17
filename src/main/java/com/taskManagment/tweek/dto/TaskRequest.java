package com.taskManagment.tweek.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level= AccessLevel.PRIVATE)
public class TaskRequest {
    String title;
    String description;
    String status;
    String priority;
    LocalDate dueDate;
    String userName;
}
