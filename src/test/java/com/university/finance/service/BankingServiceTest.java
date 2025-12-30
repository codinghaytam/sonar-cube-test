package com.university.finance.service;

import com.university.finance.model.Account;
import com.university.finance.model.User;
import com.university.finance.pattern.factory.AccountFactory;
import com.university.finance.pattern.factory.UserFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BankingServiceTest {

    private AccountFactory accountFactory;
    private UserFactory userFactory;
    private TransactionService transactionService;

    private BankingService bankingService;

    @BeforeEach
    void setUp() {
        accountFactory = mock(AccountFactory.class);
        userFactory = mock(UserFactory.class);
        transactionService = mock(TransactionService.class);

        bankingService = new BankingService(
                accountFactory,
                userFactory,
                transactionService
        );
    }


    // createUser
    @Test
    void shouldCreateUserSuccessfully() {
        User user = mock(User.class);

        when(userFactory.createUser("youssef", "secret"))
                .thenReturn(user);

        User result = bankingService.createUser("youssef", "secret");

        assertNotNull(result);
        assertEquals(user, result);

        verify(userFactory).createUser("youssef", "secret");
    }


    // createAccount
    @Test
    void shouldCreateAccountSuccessfully() {
        Account account = mock(Account.class);

        when(accountFactory.createAccount("youssef", 1000.0))
                .thenReturn(account);

        Account result = bankingService.createAccount("youssef", 1000.0);

        assertNotNull(result);
        assertEquals(account, result);

        verify(accountFactory).createAccount("youssef", 1000.0);
    }


    // deposit
    @Test
    void shouldDepositMoney() {
        when(transactionService.deposit("youssef", 200.0))
                .thenReturn(true);

        boolean result = bankingService.deposit("youssef", 200.0);

        assertTrue(result);
        verify(transactionService).deposit("youssef", 200.0);
    }


    // withdraw
    @Test
    void shouldWithdrawMoney() {
        when(transactionService.withdraw("youssef", 100.0))
                .thenReturn(true);

        boolean result = bankingService.withdraw("youssef", 100.0);

        assertTrue(result);
        verify(transactionService).withdraw("youssef", 100.0);
    }


    // transfer
    @Test
    void shouldTransferMoney() {
        when(transactionService.transfer("youssef", "ahmed", 300.0))
                .thenReturn(true);

        boolean result = bankingService.transfer("youssef", "ahmed", 300.0);

        assertTrue(result);
        verify(transactionService).transfer("youssef", "ahmed", 300.0);
    }


    // getBalance
    @Test
    void shouldReturnAccountBalance() {
        Account account = mock(Account.class);
        when(account.getBalance()).thenReturn(1500.0);

        // injecter le compte via createAccount
        when(accountFactory.createAccount("youssef", 1500.0))
                .thenReturn(account);

        bankingService.createAccount("youssef", 1500.0);

        double balance = bankingService.getBalance("youssef");

        assertEquals(1500.0, balance);
    }


    // getUser
    @Test
    void shouldReturnUserByUsername() {
        User user = mock(User.class);

        when(userFactory.createUser("youssef", "pwd"))
                .thenReturn(user);

        bankingService.createUser("youssef", "pwd");

        User result = bankingService.getUser("youssef");

        assertNotNull(result);
        assertEquals(user, result);
    }
}
