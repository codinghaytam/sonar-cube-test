package com.university.finance.pattern.command;

import com.university.finance.model.Transaction;
import com.university.finance.service.BankingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import static org.mockito.Mockito.*;

class TransactionHistoryCommandTest {

    private BankingService bankingService;
    private Scanner scanner;
    private TransactionHistoryCommand command;

    @BeforeEach
    void setUp() {
        bankingService = mock(BankingService.class);
        scanner = mock(Scanner.class);

        command = new TransactionHistoryCommand(bankingService, scanner);
    }

    @Test
    void shouldDisplayMessageWhenNoTransactionsExist() {
        // Simuler aucun historique
        when(bankingService.getTransactionHistory()).thenReturn(Collections.emptyList());

        // Exécution
        command.execute();

        // Vérification
        verify(bankingService).getTransactionHistory();
    }

    @Test
    void shouldDisplayTransactionsWhenHistoryExists() {
        // Simuler une liste de transactions
        Transaction transaction1 = mock(Transaction.class);
        Transaction transaction2 = mock(Transaction.class);

        when(transaction1.getTransactionDate()).thenReturn(null);
        when(transaction1.getTransactionType()).thenReturn("DEPOSIT");
        when(transaction1.getAccountId()).thenReturn("youssef");

        when(transaction2.getTransactionDate()).thenReturn(null);
        when(transaction2.getTransactionType()).thenReturn("WITHDRAW");
        when(transaction2.getAccountId()).thenReturn("youssef");

        List<Transaction> transactions = List.of(transaction1, transaction2);
        when(bankingService.getTransactionHistory()).thenReturn(transactions);

        // Exécution
        command.execute();

        // Vérifications
        verify(bankingService).getTransactionHistory();
        verify(transaction1).getTransactionType();
        verify(transaction1).getAccountId();
        verify(transaction2).getTransactionType();
        verify(transaction2).getAccountId();
    }
}
