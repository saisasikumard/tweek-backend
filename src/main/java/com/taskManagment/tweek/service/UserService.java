package com.taskManagment.tweek.service;

import com.taskManagment.tweek.dto.UserRegisterRequest;
import com.taskManagment.tweek.entity.Users;
import com.taskManagment.tweek.repository.UsersRepository;
import com.taskManagment.tweek.util.CommonMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    @Autowired
    UsersRepository usersRepository;
    public String test() {
        return "Welcome...";
    }
    PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
    public String addUser(UserRegisterRequest userRequest) {
        String  conanicalId= CommonMethods.getConanicalId();
        Users user=Users.builder()
                            .id(conanicalId)
                            .role("ROLE_USER")
                            .email(userRequest.getEmail())
                            .username(userRequest.getUsername())
                            .password(passwordEncoder.encode(userRequest.getPassword()))
                            .build();
        usersRepository.save(user);
        return "success";
    }

}
