package com.revature.weekTen.IntegrationTestingExample;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {}
