package com.university.finance.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.university.finance.model.Account;
import com.university.finance.model.Transaction;
import com.university.finance.model.User;
import com.university.finance.pattern.factory.AccountFactory;
import com.university.finance.pattern.factory.UserFactory;
import com.university.finance.pattern.observer.AuditLogger;
import com.university.finance.pattern.observer.NotificationService;
import com.university.finance.pattern.observer.TransactionObserver;

import io.prometheus.client.Counter;

// Le contexte de notre pattern Command
// Ce service gère les collections d'utilisateurs et les opérations s'effectuant selon le choix de l'utilisateur lors de l'utilisation du menu
// Gère l'ajout des utilisateurs à la collection des utilisateurs lors de chaque création
// Ajoute les observateurs à la liste des observateurs
// Gère la journalisation des transactions selon l'opération adaptée

public class BankingService {
    private Map<String, Account> accounts = new HashMap<>();
    private Map<String, User> users = new HashMap<>();
    private List<TransactionObserver> observers = new ArrayList<>();
    private List<Transaction> transactions = new ArrayList<>();
    private TransactionService transactionService; // Service de transaction
    
    // Définir les compteurs Prometheus
    private static final Counter usersTotal = Counter.build()
            .name("finance_users_total")
            .help("Total number of users")
            .register();
    
    private static final Counter accountsTotal = Counter.build()
            .name("finance_accounts_total")
            .help("Total number of accounts")
            .register();
    
    private static final Counter transactionsTotal = Counter.build()
            .name("finance_transactions_total")
            .help("Total number of transactions")
            .register();
    
    // Les usines ou Factories de création d'utilisateurs et comptes
    private AccountFactory accountFactory;
    private UserFactory userFactory;
    
    public BankingService(AccountFactory accountFactory, UserFactory userFactory, TransactionService transactionService) {
        this.accountFactory = accountFactory;
        this.userFactory = userFactory;
        this.transactionService = transactionService;
        
        // Ajout ou subscription des observateurs
        observers.add(new AuditLogger());
        observers.add(new NotificationService());
    }
    
    public User createUser(String username, String password) {
        User user = userFactory.createUser(username, password);
        users.put(username, user);
        usersTotal.inc(); // Incrémenter le compteur d'utilisateurs
        notifyObservers("User created: " + username);
        return user;
    }
    
    public Account createAccount(String userId, double initialBalance) {
        Account account = accountFactory.createAccount(userId, initialBalance);
        accounts.put(userId, account);
        accountsTotal.inc(); // Incrémenter le compteur de comptes
        transactions.add(new Transaction(
            UUID.randomUUID().toString(),
            userId,
            "CREATE_ACCOUNT",
            initialBalance,
            "Account created with initial balance: " + initialBalance
        ));
        transactionsTotal.inc(); // Incrémenter le compteur de transactions
        notifyObservers("Account created for user: " + userId + " with balance: " + initialBalance);
        return account;
    }
    
    public boolean deposit(String accountId, double amount) {
        if (transactionService == null) {
            System.out.println("Service de transaction non initialisé");
            return false;
        }
        boolean result = transactionService.deposit(accountId, amount);
        if (result) {
            // Notifier les observateurs
            notifyObservers("Dépôt de " + amount + " effectué sur le compte " + accountId);
        }
        return result;
    }
    
    public boolean withdraw(String accountId, double amount) {
        if (transactionService == null) {
            System.out.println("Service de transaction non initialisé");
            return false;
        }
        boolean result = transactionService.withdraw(accountId, amount);
        if (result) {
            // Notifier les observateurs
            notifyObservers("Retrait de " + amount + " effectué du compte " + accountId);
        }
        return result;
    }
    
    public boolean transfer(String fromAccountId, String toAccountId, double amount) {
        if (transactionService == null) {
            System.out.println("Service de transaction non initialisé");
            return false;
        }
        boolean result = transactionService.transfer(fromAccountId, toAccountId, amount);
        if (result) {
            // Notifier les observateurs
            notifyObservers("Transfert de " + amount + " du compte " + fromAccountId + " vers le compte " + toAccountId + " effectué");
        }

        return result;
    }
    
    public double getBalance(String accountId) {
        Account account = accounts.get(accountId);
        return account != null ? account.getBalance() : 0.0;
    }
    
    public Account getAccount(String accountId) {
        return accounts.get(accountId);
    }
    
    public User getUser(String username) {
        return users.get(username);
    }
    
    public List<Transaction> getTransactionHistory() {
        return new ArrayList<>(transactions);
    }
    
    // Méthodes supplémentaires pour le pattern commande
    public Collection<User> getAllUsers() {
        return users.values();
    }
    
    public Collection<Account> getAllAccounts() {
        return accounts.values();
    }
    
    // Méthodes supplémentaires pour le service de transactions
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        transactionsTotal.inc(); // Incrémenter le compteur de transactions
    }
    
    // Méthode pour enregistrer les transactions 
    public void recordTransaction(String id, String accountId, String type, double amount, String description) {
        Transaction transaction = new Transaction(id, accountId, type, amount, description);
        addTransaction(transaction);
    }
    
    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
    
    public void notifyObservers(String message) {
        for (TransactionObserver observer : observers) {
            observer.update(message);
        }
    }
}