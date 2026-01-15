package com.taskManagment.tweek.integration;

import com.taskManagment.tweek.config.JwtService;
import com.taskManagment.tweek.dto.TaskRequest;
import com.taskManagment.tweek.dto.TaskResponse;
import com.taskManagment.tweek.entity.Task;
import com.taskManagment.tweek.entity.Users;
import com.taskManagment.tweek.repository.TaskRepository;
import com.taskManagment.tweek.repository.UsersRepository;
import com.taskManagment.tweek.util.CommonMethods;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Transactional
public class TaskIntegrationTest {
    @LocalServerPort
    private int port;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UsersRepository usersRepository;

    private String baseUrl;
    private String jwtToken;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port;
        taskRepository.deleteAll();
        usersRepository.deleteAll();

        // Create test user in database
        String  conanicalId= CommonMethods.getConanicalId();
        Users testUser = new Users();
        testUser.setId(conanicalId);
        testUser.setUsername("john_doe");
        testUser.setPassword(passwordEncoder.encode("password123"));
        testUser.setEmail("john@example.com");
        //testUser.setRoles("ROLE_USER");  // or however you store roles
        usersRepository.save(testUser);
        // Generate token for the test user
        jwtToken = jwtService.generateToken("john_doe");
    }
    @Test
    void createTask_ShouldCreateAndReturnTask_WhenValidRequest(){
        //ARRANGE
        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setUserName("john_doe");
        taskRequest.setTitle("New Task");
        taskRequest.setDescription("Task Description");
        taskRequest.setPriority("HIGH");
        taskRequest.setStatus("PENDING");

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwtToken);

        HttpEntity<TaskRequest> entity = new HttpEntity<>(taskRequest, headers);
        //ACT
        String url=baseUrl+"/create";
        ResponseEntity<TaskResponse> actualResponse=restTemplate.postForEntity(
                url,
                entity,
                TaskResponse.class
        );
        //ASSERT
        assertEquals(HttpStatus.CREATED,actualResponse.getStatusCode());
        assertNotNull(actualResponse.getBody());
        assertNotNull(actualResponse.getBody().getTaskId());
        assertEquals("john_doe", actualResponse.getBody().getUserName());
        assertEquals("New Task", actualResponse.getBody().getTitle());
        assertEquals("Task Description", actualResponse.getBody().getDescription());
        //check in the DB
        List<Task> dbData=taskRepository.findByUserName("john_doe");
        assertEquals(1,dbData.size());
        assertEquals("Task Description",dbData.get(0).getDescription());



    }

}
