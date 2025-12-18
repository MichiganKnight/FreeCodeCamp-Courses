package com.revature.projects.SpringDatabaseAPI.Repository;

import com.revature.projects.SpringDatabaseAPI.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {}
