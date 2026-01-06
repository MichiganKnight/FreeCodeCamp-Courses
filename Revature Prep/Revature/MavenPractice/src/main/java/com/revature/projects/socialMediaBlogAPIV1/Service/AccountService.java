package com.revature.projects.socialMediaBlogAPIV1.Service;

import com.revature.projects.socialMediaBlogAPIV1.DAO.AccountDAO;
import com.revature.projects.socialMediaBlogAPIV1.Model.Account;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
    }

    public Account createAccount(Account account) {
        return accountDAO.createAccount(account);
    }

    public Account getAccount(Account account) {
        return accountDAO.getAccount(account);
    }
}
