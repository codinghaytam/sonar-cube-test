package com.university.finance.pattern.factory;

import com.university.finance.model.Account;

// Classe abstraite pour le pattern factory des comptes
// Définit le contrat pour la création de comptes
public abstract class AccountFactory {
    public abstract Account createAccount(String userId, double initialBalance);
}