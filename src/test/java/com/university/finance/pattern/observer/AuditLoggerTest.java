package com.university.finance.pattern.observer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class AuditLoggerTest {

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
    void shouldLogAuditMessage() {
        TransactionObserver observer = new AuditLogger();

        observer.update("Transaction TXN001 completed");

        String output = outputStreamCaptor.toString().trim();
        assertEquals("[AUDIT] Transaction TXN001 completed", output);
    }
}
