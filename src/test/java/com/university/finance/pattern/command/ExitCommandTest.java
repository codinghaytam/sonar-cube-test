package com.university.finance.pattern.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class ExitCommandTest {

    private MenuHandler menuHandler;
    private ExitCommand command;

    @BeforeEach
    void setUp() {
        menuHandler = mock(MenuHandler.class);
        command = new ExitCommand(menuHandler);
    }

    @Test
    void shouldStopMenuWhenExitCommandIsExecuted() {
        // Exécution
        command.execute();

        // Vérification
        verify(menuHandler).stop();
    }
}
