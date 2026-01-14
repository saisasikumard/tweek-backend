package com.taskManagment.tweek.controllerTest;

import com.taskManagment.tweek.controller.TaskController;
import com.taskManagment.tweek.dto.TaskResponse;
import com.taskManagment.tweek.entity.Task;
import com.taskManagment.tweek.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class TaskControllerTest {
    @Mock
    private TaskService taskService;

    @InjectMocks
     private TaskController taskController;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void getAllTaskDetails_ShouldReturnTaskList_WhenUserNameProvided(){
        //Arrange::prepare response
        String userName="sasi_mugiwara";
        TaskResponse taskResponse1=new TaskResponse().builder()
                .taskId("1")
                .title("testMock1")
                .description("Desc test mock1").build();
        List<TaskResponse> mockResponses= Arrays.asList(taskResponse1);
        //mock service
        when(taskService.getAllTaskDetails(userName)).thenReturn(mockResponses);
        //ACT:: call controller
        ResponseEntity<List<TaskResponse>> actualResponse=taskController.getAllTaskDetails(userName);
        //Assert
        assertNotNull(actualResponse);
        assertEquals(HttpStatus.OK,actualResponse.getStatusCode());
        assertEquals("1",actualResponse.getBody().get(0).getTaskId());
        assertEquals(mockResponses,actualResponse.getBody());
        //verify
        verify(taskService, times(1)).getAllTaskDetails(userName);
    }
}
