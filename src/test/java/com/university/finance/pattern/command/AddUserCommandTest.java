package com.university.finance.pattern.command;

import com.university.finance.model.Account;
import com.university.finance.model.User;
import com.university.finance.service.BankingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Scanner;

import static org.mockito.Mockito.*;

class AddUserCommandTest {

    private BankingService bankingService;
    private Scanner scanner;
    private AddUserCommand command;

    @BeforeEach
    void setUp() {
        bankingService = mock(BankingService.class);
        scanner = mock(Scanner.class);

        command = new AddUserCommand(bankingService, scanner);
    }

    @Test
    void shouldCreateNewUserAndAccount() {
        // Simuler la saisie utilisateur
        when(scanner.nextLine())
                .thenReturn("youssef")   // username
                .thenReturn("secret")    // password
                .thenReturn("1000");     // solde initial

        // Simuler le service
        when(bankingService.getUser("youssef")).thenReturn(null);
        when(bankingService.createUser("youssef", "secret")).thenReturn(mock(User.class));
        when(bankingService.createAccount("youssef", 1000.0)).thenReturn(mock(Account.class));

        // Exécution
        command.execute();

        // Vérifications
        verify(bankingService).getUser("youssef");
        verify(bankingService).createUser("youssef", "secret");
        verify(bankingService).createAccount("youssef", 1000.0);
    }
}
