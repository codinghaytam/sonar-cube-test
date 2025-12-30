package com.university.finance.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;   // interface User, pas l’implémentation

    @BeforeEach
    void setUp() {
        user = new ConcreteUser("youssef", "secret123");
    }

    @Test
    void shouldReturnUsername() {
        assertEquals("youssef", user.getUsername());
    }

    @Test
    void shouldReturnPassword() {
        assertEquals("secret123", user.getPassword());
    }
}
