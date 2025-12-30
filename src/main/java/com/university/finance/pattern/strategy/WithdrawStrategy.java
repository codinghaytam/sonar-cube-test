package com.university.finance.pattern.strategy;

import com.university.finance.model.Account;

// Stratégie pour effectuer un retrait d'un compte
public class WithdrawStrategy implements TransactionStrategy {
    // Exécute l'opération de retrait
    @Override
    public void execute(Account account, double amount) {
        account.withdraw(amount);
    }
}