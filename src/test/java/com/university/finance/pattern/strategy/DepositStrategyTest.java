package com.university.finance.pattern.strategy;

import com.university.finance.model.Account;
import com.university.finance.model.ConcreteAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DepositStrategyTest {

    private TransactionStrategy strategy;
    private Account account;

    @BeforeEach
    void setUp() {
        strategy = new DepositStrategy();
        account = new ConcreteAccount("ACC001", 1000.0);
    }

    @Test
    void shouldDepositAmountUsingStrategy() {
        strategy.execute(account, 300.0);

        assertEquals(1300.0, account.getBalance());
    }
}
