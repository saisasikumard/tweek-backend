package com.taskManagment.tweek.service;

import com.taskManagment.tweek.customException.InvalidTaskRequestException;
import com.taskManagment.tweek.customException.TaskNotCreatedException;
import com.taskManagment.tweek.dto.TaskRequest;
import com.taskManagment.tweek.dto.TaskResponse;
import com.taskManagment.tweek.entity.Task;
import com.taskManagment.tweek.repository.JDBCDynaRepository;
import com.taskManagment.tweek.repository.TaskRepository;
import com.taskManagment.tweek.util.CommonMethods;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TaskService {
    private final JDBCDynaRepository jdbcDynaRepository;
    private final TaskRepository taskRepository;

    private static final Logger logger = LoggerFactory.getLogger(JDBCDynaRepository.class);


    public TaskService(JDBCDynaRepository jdbcDynaRepository, TaskRepository taskRepository) {
        this.jdbcDynaRepository = jdbcDynaRepository;
        this.taskRepository = taskRepository;
    }
    public TaskResponse createTask(TaskRequest taskRequest){
        validateTaskRequest(taskRequest);
        Task task= Task.builder()
                .taskId(CommonMethods.getConanicalId())
                .title(taskRequest.getTitle())
                .description(taskRequest.getDescription())
                .dueDate(taskRequest.getDueDate())
                .status(taskRequest.getStatus())
                .userName(taskRequest.getUserName())
                .priority(taskRequest.getPriority())
                .build();

        try {
            Task savedTask = taskRepository.save(task);
            return mapToResponse(savedTask);
        } catch (Exception e) {
            throw new TaskNotCreatedException("Unable to create task: " + e.getMessage());
        }

    }
    public List<TaskResponse> getAllTaskDetails(String userName) {
        String sql="SELECT * FROM task where userName= :userName order by dueDate";


        Map<String, Object> params = new HashMap<>();
        params.put("userName", userName);

        List<Map<String, Object>> results = jdbcDynaRepository.runQuery(sql, params);
        List<TaskResponse> allTasks=new ArrayList<>();
        for(Map<String,Object> row:results){
            Task task=mapRowToEntity(row);
            TaskResponse taskResponse= mapToResponse(task);
            allTasks.add(taskResponse);
        }
        logger.info("Found {} tasks for username: {}", allTasks.size(), userName);
        return allTasks;
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
                .build();
    }
    private Task mapRowToEntity(Map<String, Object> row) {
        //Task task = new Task();

        Object taskIdObj = row.get("taskId");
        return Task.builder()
                .taskId((String) row.get("taskId"))
                .title((String) row.get("title"))
                .description((String) row.get("description"))
                .dueDate(row.get("dueDate")!=null?((java.sql.Date) row.get("dueDate")).toLocalDate():null)
                .priority((String) row.get("priority"))
                .userName((String) row.get("userName"))
                .status((String) row.get("status"))
                .build();

    }


}
