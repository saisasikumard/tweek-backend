package com.taskManagment.tweek.repository;

import com.taskManagment.tweek.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users,String> {
    public Users findByUsername(String username);
}
