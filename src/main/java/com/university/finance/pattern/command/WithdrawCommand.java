package com.university.finance.pattern.command;

import java.util.Scanner;

import com.university.finance.model.Account;
import com.university.finance.service.BankingService;

// Commande pour effectuer un retrait d'un compte
public class WithdrawCommand implements Command {
    private BankingService bankingService;
    private Scanner scanner;
    
    // Constructeur avec injection de dépendances
    public WithdrawCommand(BankingService bankingService, Scanner scanner) {
        this.bankingService = bankingService;
        this.scanner = scanner;
    }
    
    // Exécute la commande de retrait
    @Override
    public void execute() {
        System.out.print("Nom utilisateur: ");
        String username = scanner.nextLine();
        
        Account account = bankingService.getAccount(username);
        if (account != null) {
            System.out.print("Montant à retirer: ");
            try {
                double amount = Double.parseDouble(scanner.nextLine());
                if (amount > 0) {
                    double balance = bankingService.getBalance(username);
                    if (balance >= amount) {
                        bankingService.withdraw(username, amount);
                        System.out.println("Retrait réussi. Nouveau solde: $" + bankingService.getBalance(username));
                    } else {
                        System.out.println("Solde insuffisant. Solde actuel: $" + balance);
                    }
                } else {
                    System.out.println("Le montant doit être positif.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Montant invalide.");
            }
        } else {
            System.out.println("Compte non trouvé pour l'utilisateur: " + username);
        }
    }
}