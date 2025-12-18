package com.revature.projects.SpringDatabaseAPI.Service;

import com.revature.projects.SpringDatabaseAPI.Model.User;
import com.revature.projects.SpringDatabaseAPI.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
