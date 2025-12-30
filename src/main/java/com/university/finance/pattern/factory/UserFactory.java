package com.university.finance.pattern.factory;

import com.university.finance.model.User;

// Classe abstraite pour le pattern factory des utilisateurs
public abstract class UserFactory {
    public abstract User createUser(String username, String password);
}