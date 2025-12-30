package com.university.finance.pattern.command;

import com.university.finance.model.Account;
import com.university.finance.service.BankingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.mockito.Mockito.*;

class TransferCommandTest {

    private BankingService bankingService;
    private Scanner scanner;
    private TransferCommand command;

    @BeforeEach
    void setUp() {
        bankingService = mock(BankingService.class);
        scanner = mock(Scanner.class);

        command = new TransferCommand(bankingService, scanner);
    }

    @Test
    void shouldTransferMoneyBetweenTwoAccounts() {


        // Simuler la saisie utilisateur
        when(scanner.nextLine())
                .thenReturn("youssef")  // expéditeur
                .thenReturn("ali")      // destinataire
                .thenReturn("500");     // montant


        // Mocks des comptes
        Account fromAccount = mock(Account.class);
        Account toAccount = mock(Account.class);

        when(bankingService.getAccount("youssef")).thenReturn(fromAccount);
        when(bankingService.getAccount("ali")).thenReturn(toAccount);


        // Soldes
        when(bankingService.getBalance("youssef")).thenReturn(1000.0);
        when(bankingService.getBalance("ali")).thenReturn(300.0);


        // Exécution
        command.execute();


        // Vérifications
        verify(bankingService).getAccount("youssef");
        verify(bankingService).getAccount("ali");

        // Appelé 2 fois (avant + après transfert)
        verify(bankingService, times(2)).getBalance("youssef");

        verify(bankingService).transfer("youssef", "ali", 500.0);
        verify(bankingService).getBalance("ali");

        verifyNoMoreInteractions(bankingService);
    }
}
