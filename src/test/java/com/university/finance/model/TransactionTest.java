package com.university.finance.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {

    private Transaction transaction;

    @BeforeEach
    void setUp() {
        transaction = new Transaction(
                "TXN001",
                "ACC001",
                "DEPOSIT",
                500.0,
                "Initial deposit"
        );
    }

    @Test
    void shouldReturnTransactionId() {
        assertEquals("TXN001", transaction.getTransactionId());
    }

    @Test
    void shouldReturnAccountId() {
        assertEquals("ACC001", transaction.getAccountId());
    }

    @Test
    void shouldReturnTransactionType() {
        assertEquals("DEPOSIT", transaction.getTransactionType());
    }

    @Test
    void shouldReturnAmount() {
        assertEquals(500.0, transaction.getAmount());
    }

    @Test
    void shouldReturnDescription() {
        assertEquals("Initial deposit", transaction.getDescription());
    }

    @Test
    void shouldInitializeTransactionDate() {
        Date transactionDate = transaction.getTransactionDate();
        assertNotNull(transactionDate);
    }

    @Test
    void shouldHaveValidToString() {
        String result = transaction.toString();
        assertTrue(result.contains("TXN001"));
        assertTrue(result.contains("ACC001"));
        assertTrue(result.contains("DEPOSIT"));
    }
}
