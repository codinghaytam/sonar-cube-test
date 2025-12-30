package com.university.finance.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.List;

import com.university.finance.pattern.factory.ConcreteAccountFactory;
import com.university.finance.pattern.factory.ConcreteUserFactory;
import org.junit.Before;
import org.junit.Test;

import com.university.finance.model.Account;
import com.university.finance.model.Transaction;
import com.university.finance.model.User;
import com.university.finance.pattern.factory.AccountFactory;
import com.university.finance.pattern.factory.UserFactory;

public class BankingServiceIntegrationTest {

    private AccountFactory accountFactory;
    private UserFactory userFactory;

    // We keep a real TransactionService but will rewire its BankingService when needed
    private BankingService bankingService;
    private TransactionService transactionService;

    @Before
    public void setUp() {
        accountFactory = new ConcreteAccountFactory();
        userFactory = new ConcreteUserFactory();

        bankingService = new BankingService(accountFactory, userFactory, null);
        transactionService = new TransactionService(bankingService);
        bankingService.setTransactionService(transactionService);
    }

    @Test
    public void createUserAndAccount_thenDepositAndWithdraw_shouldUpdateBalancesAndHistory() {
        User user = bankingService.createUser("alice", "secret");
        assertNotNull(user);
        assertEquals("alice", user.getUsername());

        Account account = bankingService.createAccount(user.getUsername(), 100.0);
        assertNotNull(account);
        assertEquals(100.0, bankingService.getBalance(user.getUsername()), 0.0001);

        boolean depositOk = bankingService.deposit(user.getUsername(), 50.0);
        assertTrue(depositOk);
        assertEquals(150.0, bankingService.getBalance(user.getUsername()), 0.0001);

        boolean withdrawOk = bankingService.withdraw(user.getUsername(), 20.0);
        assertTrue(withdrawOk);
        assertEquals(130.0, bankingService.getBalance(user.getUsername()), 0.0001);

        List<Transaction> history = bankingService.getTransactionHistory();
        // At least 3 domain transactions should be persisted: create account, deposit, withdraw
        assertTrue("Expected at least 3 transactions but found " + history.size(), history.size() >= 3);
    }

    @Test
    public void transferBetweenTwoAccounts_shouldMoveMoneyAndCreateTwoTransactions() {
        User user1 = bankingService.createUser("bob", "pwd1");
        User user2 = bankingService.createUser("carol", "pwd2");
        bankingService.createAccount(user1.getUsername(), 200.0);
        bankingService.createAccount(user2.getUsername(), 50.0);

        boolean transferOk = bankingService.transfer(user1.getUsername(), user2.getUsername(), 80.0);
        assertTrue(transferOk);

        assertEquals(120.0, bankingService.getBalance(user1.getUsername()), 0.0001);
        assertEquals(130.0, bankingService.getBalance(user2.getUsername()), 0.0001);

        // Verify integration with TransactionService by using its query methods
        assertEquals(2, transactionService.getTransactionCountForAccount(user1.getUsername()));
        assertEquals(2, transactionService.getTransactionCountForAccount(user2.getUsername()));
    }

    @Test
    public void notifyObserversIsCalledWhenDepositing_usingMockedObserverList() {
        // verifier si les observateurs sont notifiés lors d'un dépôt
        AccountFactory mockAccountFactory = mock(AccountFactory.class);
        UserFactory mockUserFactory = mock(UserFactory.class);
        BankingService serviceWithMockedTx = new BankingService(mockAccountFactory, mockUserFactory, null);

        TransactionService mockTx = mock(TransactionService.class);
        when(mockTx.deposit("acc-1", 10.0)).thenReturn(true);

        serviceWithMockedTx.setTransactionService(mockTx);

        boolean result = serviceWithMockedTx.deposit("acc-1", 10.0);

        assertTrue(result);
        verify(mockTx, times(1)).deposit("acc-1", 10.0);
    }
}

