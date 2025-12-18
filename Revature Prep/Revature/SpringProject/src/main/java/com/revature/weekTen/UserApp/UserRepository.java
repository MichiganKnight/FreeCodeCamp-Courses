package com.revature.weekTen.UserApp;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository {
    User save(User user);
    Optional<User> findById(long id);
    List<User> findAll();
    void delete(User user);
}
