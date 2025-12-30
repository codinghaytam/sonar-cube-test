package com.university.finance.pattern.observer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class NotificationServiceTest {

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        originalOut = System.out;
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void shouldDisplayNotificationMessage() {
        TransactionObserver observer = new NotificationService();

        observer.update("Transaction TXN002 successful");

        String output = outputStreamCaptor.toString().trim();
        assertEquals("[NOTIFICATION] Transaction TXN002 successful", output);
    }
}
