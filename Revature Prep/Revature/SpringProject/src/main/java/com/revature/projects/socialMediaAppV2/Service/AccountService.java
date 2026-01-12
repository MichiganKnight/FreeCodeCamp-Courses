package com.revature.projects.socialMediaAppV2.Service;

import com.revature.projects.socialMediaAppV2.Entity.Account;
import com.revature.projects.socialMediaAppV2.Repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public Account createAccount(Account account) {
        if (accountRepository.findByUsername(account.getUsername()).isPresent()) {
            throw new RuntimeException();
        }

        if (account.getUsername() == null ||account.getUsername().isBlank() || account.getPassword() == null || account.getPassword().length() < 4) {
            throw new RuntimeException();
        }

        return accountRepository.save(account);
    }

    public Account login(Account account) {
        Account existingAccount = accountRepository.findByUsername(account.getUsername()).orElseThrow(RuntimeException::new);

        if (!existingAccount.getPassword().equals(account.getPassword())) {
            throw new RuntimeException();
        }

        return new Account(9999, account.getUsername(), account.getPassword());
    }
}
