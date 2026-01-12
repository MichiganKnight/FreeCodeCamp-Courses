package com.revature.projects.socialMediaAppV2.Repository;

import com.revature.projects.socialMediaAppV2.Entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByUsername(String username);
}