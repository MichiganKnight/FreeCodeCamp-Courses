package com.revature.weekSix;

import com.revature.weekSix.unitTesting.bankTest.Account;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.logger.CustomLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccountTest {
    //private static final Logger LOGGER = LoggerFactory.getLogger(AccountTest.class);
    private static final CustomLogger CUSTOM_LOGGER = new CustomLogger();

    Account account = new Account();

    @Test
    void deposit() {
        account.deposit(100);
        Assertions.assertEquals(100, account.getBalance());
    }

    @Test
    void withdraw() {
        // Deposit $100
        account.deposit(100);
        CUSTOM_LOGGER.info("$100 Deposited");
        //LOGGER.info("$100 Deposited");

        double withdrawAmount = account.withdraw(100); // Fails if Over Deposited Amount
        Assertions.assertEquals(0.0, withdrawAmount);
    }
}
