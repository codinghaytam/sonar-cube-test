package com.university.finance.service;

import com.university.finance.model.Account;
import com.university.finance.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    private BankingService bankingService;
    private TransactionService transactionService;

    private Account accountFrom;
    private Account accountTo;

    @BeforeEach
    void setUp() {
        bankingService = mock(BankingService.class);
        transactionService = new TransactionService(bankingService);

        accountFrom = mock(Account.class);
        accountTo = mock(Account.class);
    }


    // DEPOSIT
    @Test
    void shouldDepositSuccessfully() {
        when(bankingService.getAccount("youssef")).thenReturn(accountFrom);

        boolean result = transactionService.deposit("youssef", 200.0);

        assertTrue(result);

        verify(accountFrom).deposit(200.0);
        verify(bankingService).recordTransaction(
                anyString(),
                eq("youssef"),
                eq("DEPOSIT"),
                eq(200.0),
                contains("Dépôt")
        );
    }

    @Test
    void shouldFailDepositWhenAccountNotFound() {
        when(bankingService.getAccount("youssef")).thenReturn(null);

        boolean result = transactionService.deposit("youssef", 200.0);

        assertFalse(result);
        verify(bankingService, never())
                .recordTransaction(any(), any(), any(), anyDouble(), any());
    }


    // WITHDRAW
    @Test
    void shouldWithdrawSuccessfully() {
        when(bankingService.getAccount("youssef")).thenReturn(accountFrom);
        when(accountFrom.getBalance()).thenReturn(500.0);

        boolean result = transactionService.withdraw("youssef", 200.0);

        assertTrue(result);

        verify(accountFrom).withdraw(200.0);
        verify(bankingService).recordTransaction(
                anyString(),
                eq("youssef"),
                eq("WITHDRAW"),
                eq(200.0),
                contains("Retrait")
        );
    }

    @Test
    void shouldFailWithdrawWhenInsufficientBalance() {
        when(bankingService.getAccount("youssef")).thenReturn(accountFrom);
        when(accountFrom.getBalance()).thenReturn(100.0);

        boolean result = transactionService.withdraw("youssef", 200.0);

        assertFalse(result);
        verify(accountFrom, never()).withdraw(anyDouble());
    }

    // TRANSFER
    @Test
    void shouldTransferSuccessfully() {
        when(bankingService.getAccount("youssef")).thenReturn(accountFrom);
        when(bankingService.getAccount("ahmed")).thenReturn(accountTo);
        when(accountFrom.getBalance()).thenReturn(1000.0);

        boolean result = transactionService.transfer("youssef", "ahmed", 300.0);

        assertTrue(result);

        //  IMPORTANT : transfer() est appelé (pas withdraw/deposit)
        verify(accountFrom).transfer(accountTo, 300.0);

        // Deux transactions : TRANSFER_OUT + TRANSFER_IN
        verify(bankingService, times(2)).recordTransaction(
                anyString(),
                anyString(),
                anyString(),
                eq(300.0),
                anyString()
        );
    }

    @Test
    void shouldFailTransferWhenDestinationAccountNotFound() {
        when(bankingService.getAccount("youssef")).thenReturn(accountFrom);
        when(bankingService.getAccount("ahmed")).thenReturn(null);

        boolean result = transactionService.transfer("youssef", "ahmed", 300.0);

        assertFalse(result);
        verify(accountFrom, never()).transfer(any(), anyDouble());
    }


    // TRANSACTION HISTORY
    @Test
    void shouldReturnTransactionHistory() {
        List<Transaction> transactions = List.of(mock(Transaction.class));

        when(bankingService.getTransactionHistory()).thenReturn(transactions);

        List<Transaction> result = transactionService.getTransactionHistory();

        assertEquals(1, result.size());
    }

    @Test
    void shouldReturnTransactionCount() {
        when(bankingService.getTransactionHistory())
                .thenReturn(List.of(
                        mock(Transaction.class),
                        mock(Transaction.class)
                ));

        int count = transactionService.getTransactionCount();

        assertEquals(2, count);
    }
}
