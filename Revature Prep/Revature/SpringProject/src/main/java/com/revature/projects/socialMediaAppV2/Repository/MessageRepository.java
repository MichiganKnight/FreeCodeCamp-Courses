package com.revature.projects.socialMediaAppV2.Repository;

import com.revature.projects.socialMediaAppV2.Entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {}