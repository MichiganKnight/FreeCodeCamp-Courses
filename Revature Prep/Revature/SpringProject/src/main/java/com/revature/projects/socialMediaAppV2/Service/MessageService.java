package com.revature.projects.socialMediaAppV2.Service;

import com.revature.projects.socialMediaAppV2.Entity.Message;
import com.revature.projects.socialMediaAppV2.Repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    public Message createMessage(Message message) {
        if (message.getMessageText() == null  || message.getMessageText().isBlank() || message.getMessageText().length() > 255 || message.getPostedBy() <= 0) {
            throw new RuntimeException();
        }

        return messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public List<Message> getMessagesByAccountId(long accountId) {
        List<Message> messages = new ArrayList<>();

        for (Message message : messageRepository.findAll()) {
            if (message.getPostedBy() == accountId) {
                messages.add(message);
            }
        }

        return messages;
    }

    public int updateMessage(int messageId, Message message) {
        Message existingMessage = messageRepository.findById(messageId).orElseThrow(RuntimeException::new);

        if (message.getMessageText() == null  || message.getMessageText().isBlank() || message.getMessageText().length() > 255) {
            throw new RuntimeException();
        }

        existingMessage.setMessageText(message.getMessageText());
        messageRepository.save(existingMessage);

        return 1;
    }

    public int deleteMessage(int messageId) {
        if (messageRepository.existsById(messageId)) {
            messageRepository.deleteById(messageId);
            return 1;
        }

        return 0;
    }
}
