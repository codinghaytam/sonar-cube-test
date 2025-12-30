package com.university.finance.pattern.strategy;

import com.university.finance.model.Account;

// Interface pour le pattern stratégie des transactions
// Définit le contrat pour toutes les opérations de transaction
public interface TransactionStrategy {
    void execute(Account account, double amount);
}