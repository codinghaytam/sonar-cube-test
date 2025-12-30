package com.university.finance.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.university.finance.model.Account;
import com.university.finance.model.Transaction;
import com.university.finance.pattern.factory.ConcreteAccountFactory;
import com.university.finance.pattern.factory.ConcreteUserFactory;

public class TransactionServiceIntegrationTest {

    private BankingService bankingService;
    private TransactionService transactionService;

    @Before
    public void setUp() {
        ConcreteAccountFactory accountFactory = new ConcreteAccountFactory();
        ConcreteUserFactory userFactory = new ConcreteUserFactory();
        bankingService = new BankingService(accountFactory, userFactory, null);
        transactionService = new TransactionService(bankingService);
        bankingService.setTransactionService(transactionService);
    }

    @Test
    public void deposit_withValidAmount_shouldIncreaseBalanceAndPersistTransaction() {
        bankingService.createUser("haytam", "pwd");
        bankingService.createAccount("haytam", 100.0);

        boolean ok = transactionService.deposit("haytam", 40.0);
        assertTrue(ok);

        assertEquals(140.0, bankingService.getBalance("haytam"), 0.0001);

        List<Transaction> forAccount = transactionService.getTransactionHistoryForAccount("haytam");
        assertFalse(forAccount.isEmpty());
        assertEquals("DEPOSIT", forAccount.get(forAccount.size() - 1).getTransactionType());
    }

    @Test
    public void withdraw_withSufficientFunds_shouldDecreaseBalanceAndPersistTransaction() {
        bankingService.createUser("erin", "pwd");
        bankingService.createAccount("erin", 150.0);

        boolean ok = transactionService.withdraw("erin", 40.0);
        assertTrue(ok);

        assertEquals(110.0, bankingService.getBalance("erin"), 0.0001);

        List<Transaction> forAccount = transactionService.getTransactionHistoryForAccount("erin");
        assertFalse(forAccount.isEmpty());
        assertEquals("WITHDRAW", forAccount.get(forAccount.size() - 1).getTransactionType());
    }

    @Test
    public void transfer_withSufficientFunds_shouldMoveMoneyAndPersistTwoTransactions() {
        bankingService.createUser("frank", "pwd1");
        bankingService.createUser("gina", "pwd2");
        bankingService.createAccount("frank", 300.0);
        bankingService.createAccount("gina", 10.0);

        boolean ok = transactionService.transfer("frank", "gina", 70.0);
        assertTrue(ok);

        assertEquals(230.0, bankingService.getBalance("frank"), 0.0001);
        assertEquals(80.0, bankingService.getBalance("gina"), 0.0001);

        List<Transaction> frankTx = transactionService.getTransactionHistoryForAccount("frank");
        List<Transaction> ginaTx = transactionService.getTransactionHistoryForAccount("gina");
        assertFalse(frankTx.isEmpty());
        assertFalse(ginaTx.isEmpty());

        assertEquals("TRANSFER_OUT", frankTx.get(frankTx.size() - 1).getTransactionType());
        assertEquals("TRANSFER_IN", ginaTx.get(ginaTx.size() - 1).getTransactionType());
    }

    @Test
    public void getRecentTransactions_shouldReturnMostRecentOnesInOrder() {
        bankingService.createUser("henry", "pwd");
        bankingService.createAccount("henry", 0.0);

        transactionService.deposit("henry", 10.0);
        transactionService.deposit("henry", 20.0);
        transactionService.deposit("henry", 30.0);

        List<Transaction> recent = transactionService.getRecentTransactions(2);
        assertEquals(2, recent.size());
        assertEquals(20.0, recent.get(0).getAmount(), 0.0001);
        assertEquals(30.0, recent.get(1).getAmount(), 0.0001);
    }
}

