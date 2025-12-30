package com.university.finance.pattern.command;

import com.university.finance.service.BankingService;
import com.university.finance.service.TransactionService;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class MenuHandlerTest {

    @Test
    void shouldStopRunningWhenExitCommandIsSelected() {
        // Simuler la saisie utilisateur : 0 (exit)
        String input = "0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        BankingService bankingService = mock(BankingService.class);
        TransactionService transactionService = mock(TransactionService.class);

        MenuHandler menuHandler = new MenuHandler(bankingService, transactionService);

        // Exécution
        menuHandler.run();

        // Vérification indirecte : si run() termine, stop() a été appelé
        assertTrue(true); // le test passe si la boucle s'arrête
    }
}
