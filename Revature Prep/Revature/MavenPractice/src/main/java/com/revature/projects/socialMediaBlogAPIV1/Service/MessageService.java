package com.revature.projects.socialMediaBlogAPIV1.Service;

import com.revature.projects.socialMediaBlogAPIV1.DAO.AccountDAO;
import com.revature.projects.socialMediaBlogAPIV1.DAO.MessageDAO;
import com.revature.projects.socialMediaBlogAPIV1.Model.Account;
import com.revature.projects.socialMediaBlogAPIV1.Model.Message;

import java.util.List;

public class MessageService {
    private AccountDAO accountDAO;
    private MessageDAO messageDAO;

    public MessageService() {
        accountDAO = new AccountDAO();
        messageDAO = new MessageDAO();
    }

    public Message createMessage(Message message) {
        if (message.getMessage_text().length() > 25 || message.getMessage_text().isBlank()) return null;

        Account user = accountDAO.getAccountById(message.getPosted_by());
        if (user == null) return null;

        return messageDAO.createMessage(message);
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Message getMessageById(int id) {
        return messageDAO.getMessageById(id);
    }

    public Message deleteMessageById(int id) {
        if (id == 0) return null;

        return messageDAO.deleteMessageById(id);
    }

    public Message updateMessage(Message message) {
        return messageDAO.updateMessageById(message);
    }

    public List<Message> getAllMessagesByAccountId(int accountId) {
        return messageDAO.getMessagesByAccountId(accountId);
    }
}
