package com.university.finance.pattern.factory;

import com.university.finance.model.User;
import com.university.finance.model.ConcreteUser;

// Implémentation concrète du pattern factory pour les utilisateurs
// Crée des instances de ConcreteUser
public class ConcreteUserFactory extends UserFactory {
    // Crée un nouvel utilisateur avec le nom d'utilisateur et le mot de passe
    @Override
    public User createUser(String username, String password) {
        return new ConcreteUser(username, password);
    }
}