package com.taskManagment.tweek.repository;

import com.taskManagment.tweek.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task,String> {


    List<Task> findByUserName(String johnDoe);
}
