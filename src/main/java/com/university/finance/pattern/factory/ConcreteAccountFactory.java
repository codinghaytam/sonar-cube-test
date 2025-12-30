package com.university.finance.pattern.factory;

import com.university.finance.model.Account;
import com.university.finance.model.ConcreteAccount;

// Implémentation concrète du pattern factory pour les comptes
// Crée des instances de ConcreteAccount
public class ConcreteAccountFactory extends AccountFactory {
    // Crée un nouveau compte avec l'identifiant utilisateur et le solde initial
    @Override
    public Account createAccount(String userId, double initialBalance) {
        return new ConcreteAccount(userId, initialBalance);
    }
}