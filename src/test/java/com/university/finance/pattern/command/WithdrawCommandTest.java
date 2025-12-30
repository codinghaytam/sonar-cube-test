package com.university.finance.pattern.command;

import com.university.finance.model.Account;
import com.university.finance.service.BankingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.mockito.Mockito.*;

class WithdrawCommandTest {

    private BankingService bankingService;
    private Scanner scanner;
    private WithdrawCommand command;

    @BeforeEach
    void setUp() {
        bankingService = mock(BankingService.class);
        scanner = mock(Scanner.class);

        command = new WithdrawCommand(bankingService, scanner);
    }

    @Test
    void shouldWithdrawMoneySuccessfully() {

        // Simuler la saisie utilisateur
        when(scanner.nextLine())
                .thenReturn("youssef") // username
                .thenReturn("300");    // montant


        // Mock du compte
        Account account = mock(Account.class);
        when(bankingService.getAccount("youssef")).thenReturn(account);


        // Solde initial
        when(bankingService.getBalance("youssef")).thenReturn(1000.0, 700.0);
        // 1er appel → vérification
        // 2e appel → affichage nouveau solde


        // Exécution
        command.execute();


        // Vérifications
        verify(bankingService).getAccount("youssef");
        verify(bankingService).withdraw("youssef", 300.0);

        verify(bankingService, times(2)).getBalance("youssef");

        verifyNoMoreInteractions(bankingService);
    }
}
