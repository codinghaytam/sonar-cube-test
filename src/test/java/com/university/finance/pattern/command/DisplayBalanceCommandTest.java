package com.university.finance.pattern.command;

import com.university.finance.model.Account;
import com.university.finance.service.BankingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.mockito.Mockito.*;

class DisplayBalanceCommandTest {

    private BankingService bankingService;
    private Scanner scanner;
    private DisplayBalanceCommand command;

    @BeforeEach
    void setUp() {
        bankingService = mock(BankingService.class);
        scanner = mock(Scanner.class);

        command = new DisplayBalanceCommand(bankingService, scanner);
    }

    @Test
    void shouldDisplayBalanceWhenAccountExists() {
        // Simuler la saisie utilisateur
        when(scanner.nextLine()).thenReturn("youssef");

        // Simuler le service et le compte
        Account account = mock(Account.class);
        when(account.getBalance()).thenReturn(1200.0);
        when(bankingService.getAccount("youssef")).thenReturn(account);

        // Exécution
        command.execute();

        // Vérifications
        verify(bankingService).getAccount("youssef");
        verify(account).getBalance();
    }

    @Test
    void shouldNotDisplayBalanceWhenAccountDoesNotExist() {
        // Simuler la saisie utilisateur
        when(scanner.nextLine()).thenReturn("youssef");

        // Compte inexistant
        when(bankingService.getAccount("youssef")).thenReturn(null);

        // Exécution
        command.execute();

        // Vérifications
        verify(bankingService).getAccount("youssef");
        verifyNoInteractions(
                mock(Account.class) // aucune interaction métier attendue
        );
    }
}
