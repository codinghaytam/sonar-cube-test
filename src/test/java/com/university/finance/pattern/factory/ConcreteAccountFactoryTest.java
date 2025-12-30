package com.university.finance.pattern.factory;

import com.university.finance.model.Account;
import com.university.finance.model.ConcreteAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConcreteAccountFactoryTest {

    private AccountFactory accountFactory;

    @BeforeEach
    void setUp() {
        accountFactory = new ConcreteAccountFactory();
    }

    @Test
    void shouldCreateAccount() {
        Account account = accountFactory.createAccount("USER001", 1000.0);

        assertNotNull(account);
        assertEquals("USER001", account.getAccountId());
        assertEquals(1000.0, account.getBalance());
    }

    @Test
    void shouldCreateConcreteAccountInstance() {
        Account account = accountFactory.createAccount("USER002", 500.0);

        assertTrue(account instanceof ConcreteAccount);
    }
}
