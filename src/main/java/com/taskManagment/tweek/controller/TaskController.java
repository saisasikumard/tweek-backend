package com.taskManagment.tweek.controller;

import com.taskManagment.tweek.customException.ProductNotFoundException;
import com.taskManagment.tweek.customException.ResourceNotFoundException;
import com.taskManagment.tweek.dto.TaskRequest;
import com.taskManagment.tweek.dto.TaskResponse;
import com.taskManagment.tweek.entity.Product;
import com.taskManagment.tweek.service.FakeProductService;
import com.taskManagment.tweek.service.TaskService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    TaskService taskService;
    @Autowired
    private FakeProductService productService;
    @PostMapping("/create")
    public ResponseEntity<TaskResponse> createTask(@RequestBody TaskRequest taskRequest){
       return  ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(taskRequest));
    }
    @GetMapping("/allTasks")
    public ResponseEntity<List<TaskResponse>> getAllTaskDetails(@RequestParam String userName){
        return  ResponseEntity.status(HttpStatus.OK).body(taskService.getAllTaskDetails(userName));
    }
    @GetMapping("/{taskId}")
    public ResponseEntity<?> getTask(@PathVariable("taskId") String taskId){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(taskService.getTask(taskId));
        }
     catch (ResourceNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", e.getMessage()));
    } catch (RuntimeException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", e.getMessage()));
    }
    }
    @PutMapping("/{taskId}")
    public ResponseEntity<?> modifyTask(@PathVariable("taskId") String taskId,@RequestBody TaskRequest taskRequest){
        try{
            return  ResponseEntity.status(HttpStatus.OK).body(taskService.modifyTask(taskRequest,taskId));
            }

        catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }
    @DeleteMapping("/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable("taskId") String taskId){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(taskService.deleteTask(taskId));
        }
        catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error",e.getMessage()));
        }
    }
    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") long id) throws ProductNotFoundException {
//        Product product = productService.getProductById(id);
//        if(product == null) {
//            return new ResponseEntity<>(product, HttpStatus.BAD_REQUEST);
//        }
//
//        return new ResponseEntity<>(product, HttpStatus.OK);
//        UserDto userDto = authCommons.validateToken(token);
//
//        if(userDto == null) {
//            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//        }
        Product product = productService.getProductById(id);
        if(product == null) {
            return new ResponseEntity<>(product, HttpStatus.BAD_REQUEST);
        }

        // Role based access
//        for(Role role : userDto.getRoles()) {
//            if(role.getValue().equals("ADMIN")) {
//                return new ResponseEntity<>(product, HttpStatus.OK);
//            }
//        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
}
