package com.university.finance.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    private Account account;
    private Account recipient;

    @BeforeEach
    void setUp() {
        account = new ConcreteAccount("ACC001", 1000.0);
        recipient = new ConcreteAccount("ACC002", 200.0);
    }

    @Test
    void shouldReturnInitialBalance() {
        // getBalance
        assertEquals(1000.0, account.getBalance());
    }

    @Test
    void shouldDepositMoney() {
        account.deposit(300.0);
        assertEquals(1300.0, account.getBalance());
    }

    @Test
    void shouldWithdrawMoney() {
        account.withdraw(400.0);
        assertEquals(600.0, account.getBalance());
    }

    @Test
    void shouldTransferMoneyToAnotherAccount() {
        account.transfer(recipient, 250.0);

        assertEquals(750.0, account.getBalance());
        assertEquals(450.0, recipient.getBalance());
    }

    @Test
    void shouldReturnAccountId() {
        assertEquals("ACC001", account.getAccountId());
    }
}
