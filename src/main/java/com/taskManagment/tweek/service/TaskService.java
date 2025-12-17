package com.taskManagment.tweek.service;

import com.taskManagment.tweek.customException.InvalidTaskRequestException;
import com.taskManagment.tweek.customException.TaskNotCreatedException;
import com.taskManagment.tweek.dto.TaskRequest;
import com.taskManagment.tweek.dto.TaskResponse;
import com.taskManagment.tweek.entity.Task;
import com.taskManagment.tweek.repository.TaskRepository;
import com.taskManagment.tweek.util.CommonMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    public TaskResponse createTask(TaskRequest taskRequest){
        validateTaskRequest(taskRequest);
        Task task= Task.builder().taskId(CommonMethods.getConanicalId()).title(taskRequest.getTitle())
                .description(taskRequest.getDescription()).dueDate(taskRequest.getDueDate()).status(taskRequest.getStatus())
                .userName(taskRequest.getUserName()).build();

        try {
            Task savedTask = taskRepository.save(task);
            return mapToResponse(savedTask);
        } catch (Exception e) {
            throw new TaskNotCreatedException("Unable to create task: " + e.getMessage());
        }

    }
    private void validateTaskRequest(TaskRequest taskRequest) {
        if (taskRequest.getTitle() == null || taskRequest.getTitle().trim().isEmpty()) {
            throw new InvalidTaskRequestException("Task title is required");
        }
        if (taskRequest.getUserName() == null || taskRequest.getUserName().trim().isEmpty()) {
            throw new InvalidTaskRequestException("Username is required");
        }
    }
    private TaskResponse mapToResponse(Task task) {
        return TaskResponse.builder()
                .taskId(task.getTaskId())
                .title(task.getTitle())
                .description(task.getDescription())
                .dueDate(task.getDueDate())
                .status(task.getStatus())
                .userName(task.getUserName())
                .message("Task created successfully")
                .build();
    }
}
