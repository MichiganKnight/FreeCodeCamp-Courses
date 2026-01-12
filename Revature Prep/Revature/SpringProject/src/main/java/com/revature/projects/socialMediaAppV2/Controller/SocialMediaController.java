package com.revature.projects.socialMediaAppV2.Controller;

import com.revature.projects.socialMediaAppV2.Entity.*;
import com.revature.projects.socialMediaAppV2.Repository.*;
import com.revature.projects.socialMediaAppV2.Service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class SocialMediaController {
    private final AccountService accountService;
    private final MessageService messageService;
    private final MessageRepository messageRepository;

    public SocialMediaController(AccountService accountService, MessageService messageService, MessageRepository messageRepository) {
        this.accountService = accountService;
        this.messageService = messageService;
        this.messageRepository = messageRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody Account account) {
        try {
            Account created = accountService.createAccount(account);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            return ResponseEntity.status(409).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account) {
        try {
            return ResponseEntity.ok(accountService.login(account));
        } catch (Exception e) {
            return ResponseEntity.status(401).build();
        }
    }

    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getMessagesByAccountId(@PathVariable int accountId) {
        return ResponseEntity.ok(messageService.getMessagesByAccountId(accountId));
    }

    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable int messageId) {
        return messageRepository.findById(messageId).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.ok().build());
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        try {
            Message created = messageService.createMessage(message);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            return ResponseEntity.status(400).build();
        }
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        return ResponseEntity.ok(messageService.getAllMessages());
    }

    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> updateMessage(@PathVariable int messageId, @RequestBody Message message) {
        try {
            return ResponseEntity.ok(messageService.updateMessage(messageId, message));
        } catch (Exception e) {
            return ResponseEntity.status(400).build();
        }
    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessage(@PathVariable int messageId) {
        int result = messageService.deleteMessage(messageId);

        if (result == 1) {
            return ResponseEntity.ok(1);
        }

        return ResponseEntity.ok().build();
    }
}
