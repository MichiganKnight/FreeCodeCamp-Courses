package com.revature.weekTen.UserApp;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User getUserById(long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("No User Found with ID: " + id));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(Long id, User user) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("No User Found with ID: " + id));

        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());

        return userRepository.save(existingUser);
    }

    public void deleteUser(long id) {
        User userToDelete = userRepository.findById(id).orElseThrow(() -> new RuntimeException("No User Found with ID: " + id));
        userRepository.delete(userToDelete);
    }
}
