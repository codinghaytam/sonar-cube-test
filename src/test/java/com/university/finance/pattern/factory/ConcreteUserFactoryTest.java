package com.university.finance.pattern.factory;

import com.university.finance.model.User;
import com.university.finance.model.ConcreteUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConcreteUserFactoryTest {

    private UserFactory userFactory;

    @BeforeEach
    void setUp() {
        userFactory = new ConcreteUserFactory();
    }

    @Test
    void shouldCreateUser() {
        User user = userFactory.createUser("youssef", "secret123");

        assertNotNull(user);
        assertEquals("youssef", user.getUsername());
        assertEquals("secret123", user.getPassword());
    }

    @Test
    void shouldCreateConcreteUserInstance() {
        User user = userFactory.createUser("amina", "pwd456");

        assertTrue(user instanceof ConcreteUser);
    }
}
