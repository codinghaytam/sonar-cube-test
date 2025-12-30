package com.university.finance.pattern.command;

import com.university.finance.model.Account;
import com.university.finance.service.BankingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.mockito.Mockito.*;

class DepositCommandTest {

    private BankingService bankingService;
    private Scanner scanner;
    private DepositCommand command;

    @BeforeEach
    void setUp() {
        bankingService = mock(BankingService.class);
        scanner = mock(Scanner.class);

        command = new DepositCommand(bankingService, scanner);
    }

    @Test
    void shouldDepositAmountWhenAccountExistsAndAmountIsValid() {
        // Simuler la saisie utilisateur
        when(scanner.nextLine())
                .thenReturn("youssef") // username
                .thenReturn("500");    // montant à déposer

        // Simuler le service
        Account account = mock(Account.class);
        when(bankingService.getAccount("youssef")).thenReturn(account);
        when(bankingService.getBalance("youssef")).thenReturn(1500.0);

        // Exécution
        command.execute();

        // Vérifications
        verify(bankingService).getAccount("youssef");
        verify(bankingService).deposit("youssef", 500.0);
        verify(bankingService).getBalance("youssef");
    }
}
