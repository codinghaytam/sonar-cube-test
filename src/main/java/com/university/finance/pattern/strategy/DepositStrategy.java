package com.university.finance.pattern.strategy;

import com.university.finance.model.Account;

// Stratégie pour effectuer un dépôt sur un compte
public class DepositStrategy implements TransactionStrategy {
    // Exécute l'opération de dépôt
    @Override
    public void execute(Account account, double amount) {
        account.deposit(amount);
    }
}