package com.university.finance.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.university.finance.model.Account;
import com.university.finance.model.Transaction;
import com.university.finance.pattern.strategy.DepositStrategy;
import com.university.finance.pattern.strategy.TransactionStrategy;
import com.university.finance.pattern.strategy.TransferStrategy;
import com.university.finance.pattern.strategy.WithdrawStrategy;

// Service spécialisé pour la gestion des opérations de transaction
// Coordonne les opérations de dépôt, retrait et transfert entre comptes
// Implémente le pattern Strategy en servant de contexte pour les stratégies de transaction
public class TransactionService {
    private BankingService bankingService;
    private TransactionStrategy currentStrategy; // Contexte pour le pattern Strategy
    
    // Constructeur avec injection de dépendance
    public TransactionService(BankingService bankingService) {
        this.bankingService = bankingService;
        this.currentStrategy = null;
    }
    
    // Définit la stratégie courante à utiliser
    public void setStrategy(TransactionStrategy strategy) {
        this.currentStrategy = strategy;
    }
    
    // Effectue un dépôt sur un compte
    public boolean deposit(String accountId, double amount) {
        Account account = bankingService.getAccount(accountId);
        if (account == null) {
            System.out.println("Compte non trouvé: " + accountId);
            return false;
        }
        
        if (amount <= 0) {
            System.out.println("Le montant du dépôt doit être positif");
            return false;
        }
        
        try {
            // Utiliser la stratégie de dépôt dans le contexte
            setStrategy(new DepositStrategy());
            currentStrategy.execute(account, amount);
            
            // Notifier le service bancaire de la transaction
            bankingService.recordTransaction(
                UUID.randomUUID().toString(),
                accountId,
                "DEPOSIT",
                amount,
                "Dépôt de " + amount + " effectué"
            );
            
            System.out.println("Dépôt de " + amount + " effectué avec succès sur le compte " + accountId);
            return true;
        } catch (Exception e) {
            System.out.println("Erreur lors du dépôt: " + e.getMessage());
            return false;
        }
    }
    
    // Effectue un retrait d'un compte
    public boolean withdraw(String accountId, double amount) {
        Account account = bankingService.getAccount(accountId);
        if (account == null) {
            System.out.println("Compte non trouvé: " + accountId);
            return false;
        }
        
        if (amount <= 0) {
            System.out.println("Le montant du retrait doit être positif");
            return false;
        }
        
        // Vérifier le solde
        if (account.getBalance() < amount) {
            System.out.println("Solde insuffisant pour le retrait");
            return false;
        }
        
        try {
            // Utiliser la stratégie de retrait dans le contexte
            setStrategy(new WithdrawStrategy());
            currentStrategy.execute(account, amount);
            
            // Notifier le service bancaire de la transaction
            bankingService.recordTransaction(
                UUID.randomUUID().toString(),
                accountId,
                "WITHDRAW",
                amount,
                "Retrait de " + amount + " effectué"
            );
            
            System.out.println("Retrait de " + amount + " effectué avec succès du compte " + accountId);
            return true;
        } catch (Exception e) {
            System.out.println("Erreur lors du retrait: " + e.getMessage());
            return false;
        }
    }
    
    // Effectue un transfert entre deux comptes
    public boolean transfer(String fromAccountId, String toAccountId, double amount) {
        Account fromAccount = bankingService.getAccount(fromAccountId);
        Account toAccount = bankingService.getAccount(toAccountId);
        
        if (fromAccount == null) {
            System.out.println("Compte source non trouvé: " + fromAccountId);
            return false;
        }
        
        if (toAccount == null) {
            System.out.println("Compte destination non trouvé: " + toAccountId);
            return false;
        }
        
        if (amount <= 0) {
            System.out.println("Le montant du transfert doit être positif");
            return false;
        }
        
        // Vérifier le solde du compte source
        if (fromAccount.getBalance() < amount) {
            System.out.println("Solde insuffisant pour le transfert");
            return false;
        }
        
        try {
            // Utiliser la stratégie de transfert dans le contexte
            setStrategy(new TransferStrategy(toAccount));
            currentStrategy.execute(fromAccount, amount);
            
            // Notifier le service bancaire des transactions
            bankingService.recordTransaction(
                UUID.randomUUID().toString(),
                fromAccountId,
                "TRANSFER_OUT",
                amount,
                "Transfert de " + amount + " vers le compte " + toAccountId
            );
            
            bankingService.recordTransaction(
                UUID.randomUUID().toString(),
                toAccountId,
                "TRANSFER_IN",
                amount,
                "Réception de " + amount + " du compte " + fromAccountId
            );
            
            System.out.println("Transfert de " + amount + " du compte " + fromAccountId + " vers le compte " + toAccountId + " effectué avec succès");
            return true;
        } catch (Exception e) {
            System.out.println("Erreur lors du transfert: " + e.getMessage());
            return false;
        }
    }
    
    // Récupère l'historique complet des transactions
    public List<Transaction> getTransactionHistory() {
        return bankingService.getTransactionHistory();
    }
    
    // Récupère l'historique des transactions pour un compte spécifique
    public List<Transaction> getTransactionHistoryForAccount(String accountId) {
        List<Transaction> allTransactions = bankingService.getTransactionHistory();
        List<Transaction> accountTransactions = new ArrayList<>();
        for (Transaction transaction : allTransactions) {
            if (transaction.getAccountId().equals(accountId)) {
                accountTransactions.add(transaction);
            }
        }
        return accountTransactions;
    }
    
    // Récupère les transactions par type (DEPOSIT, WITHDRAW, etc.)
    public List<Transaction> getTransactionsByType(String transactionType) {
        List<Transaction> allTransactions = bankingService.getTransactionHistory();
        List<Transaction> filteredTransactions = new ArrayList<>();
        for (Transaction transaction : allTransactions) {
            if (transaction.getTransactionType().equals(transactionType)) {
                filteredTransactions.add(transaction);
            }
        }
        return filteredTransactions;
    }
    
    // Récupère les transactions les plus récentes
    public List<Transaction> getRecentTransactions(int limit) {
        List<Transaction> allTransactions = bankingService.getTransactionHistory();
        int size = allTransactions.size();
        int fromIndex = Math.max(0, size - limit);
        List<Transaction> recentTransactions = new ArrayList<>();
        for (int i = fromIndex; i < size; i++) {
            recentTransactions.add(allTransactions.get(i));
        }
        return recentTransactions;
    }
    
    // Récupère le nombre total de transactions
    public int getTransactionCount() {
        return bankingService.getTransactionHistory().size();
    }
    
    // Récupère le nombre de transactions pour un compte spécifique
    public int getTransactionCountForAccount(String accountId) {
        return getTransactionHistoryForAccount(accountId).size();
    }
}