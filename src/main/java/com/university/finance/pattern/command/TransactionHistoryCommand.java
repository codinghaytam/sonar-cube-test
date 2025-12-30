package com.university.finance.pattern.command;

import java.util.List;
import java.util.Scanner;

import com.university.finance.model.Transaction;
import com.university.finance.service.BankingService;

// Commande pour afficher l'historique des transactions
public class TransactionHistoryCommand implements Command {
    private BankingService bankingService;
    private Scanner scanner;
    
    // Constructeur avec injection de dépendances
    public TransactionHistoryCommand(BankingService bankingService, Scanner scanner) {
        this.bankingService = bankingService;
        this.scanner = scanner;
    }
    
    // Exécute la commande d'affichage de l'historique des transactions
    @Override
    public void execute() {
        System.out.println("\n=== Historique des Transactions ===");
        List<Transaction> transactions = bankingService.getTransactionHistory();
        
        if (transactions.isEmpty()) {
            System.out.println("Aucune transaction trouvée.");
        } else {
            for (Transaction transaction : transactions) {
                System.out.println(transaction.getTransactionDate() + " | " + transaction.getTransactionType() + " | Compte: " + transaction.getAccountId());
            }
            System.out.println("Total: " + transactions.size() + " transactions");
        }
    }
}