package com.university.finance.pattern.observer;

// Interface pour le pattern observateur des transactions
public interface TransactionObserver {
    void update(String transactionDetails);
}