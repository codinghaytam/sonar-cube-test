package com.university.finance.pattern.command;

import java.util.Scanner;

import com.university.finance.model.Account;
import com.university.finance.service.BankingService;

// Commande pour afficher le solde d'un compte
public class DisplayBalanceCommand implements Command {
    private BankingService bankingService; // Receiver of the command
    private Scanner scanner;
    
    // Constructeur avec injection de dépendances
    public DisplayBalanceCommand(BankingService bankingService, Scanner scanner) {
        this.bankingService = bankingService;
        this.scanner = scanner;
    }
    
    // Exécute la commande d'affichage du solde
    @Override
    public void execute() {
        System.out.print("Nom utilisateur: ");
        String username = scanner.nextLine();
        
        Account account = bankingService.getAccount(username);
        if (account != null) {
            System.out.println("Solde du compte " + username + ": $" + account.getBalance());
        } else {
            System.out.println("Compte non trouvé pour l'utilisateur: " + username);
        }
    }
}