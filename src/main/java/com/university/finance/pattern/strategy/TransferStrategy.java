package com.university.finance.pattern.strategy;

import com.university.finance.model.Account;

// Stratégie pour effectuer un transfert entre deux comptes
public class TransferStrategy implements TransactionStrategy {
    private Account recipient;
    
    // Constructeur avec le compte destinataire
    public TransferStrategy(Account recipient) {
        this.recipient = recipient;
    }
    
    // Exécute l'opération de transfert
    @Override
    public void execute(Account account, double amount) {
        account.transfer(recipient, amount);
    }
}