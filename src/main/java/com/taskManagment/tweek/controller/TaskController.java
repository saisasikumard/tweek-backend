package com.taskManagment.tweek.controller;

import com.taskManagment.tweek.dto.TaskRequest;
import com.taskManagment.tweek.dto.TaskResponse;
import com.taskManagment.tweek.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    TaskService taskService;
    @PostMapping("/create")
    public ResponseEntity<TaskResponse> createTask(@RequestBody TaskRequest taskRequest){
       return  ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(taskRequest));
    }
    @GetMapping("/allTasks")
    public ResponseEntity<List<TaskResponse>> getAllTaskDetails(@RequestParam String userName){
        return  ResponseEntity.status(HttpStatus.OK).body(taskService.getAllTaskDetails(userName));
    }
}
