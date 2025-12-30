package com.university.finance.pattern.strategy;

import com.university.finance.model.Account;
import com.university.finance.model.ConcreteAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransferStrategyTest {

    private TransactionStrategy strategy;
    private Account sourceAccount;
    private Account recipientAccount;

    @BeforeEach
    void setUp() {
        recipientAccount = new ConcreteAccount("ACC002", 300.0);
        sourceAccount = new ConcreteAccount("ACC001", 1000.0);

        strategy = new TransferStrategy(recipientAccount);
    }

    @Test
    void shouldTransferAmountBetweenAccounts() {
        strategy.execute(sourceAccount, 200.0);

        assertEquals(800.0, sourceAccount.getBalance());
        assertEquals(500.0, recipientAccount.getBalance());
    }
}
