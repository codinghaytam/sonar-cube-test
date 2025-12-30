package com.university.finance.pattern.strategy;

import com.university.finance.model.Account;
import com.university.finance.model.ConcreteAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WithdrawStrategyTest {

    private TransactionStrategy strategy;
    private Account account;

    @BeforeEach
    void setUp() {
        strategy = new WithdrawStrategy();
        account = new ConcreteAccount("ACC001", 1000.0);
    }

    @Test
    void shouldWithdrawAmountUsingStrategy() {
        strategy.execute(account, 400.0);

        assertEquals(600.0, account.getBalance());
    }
}
