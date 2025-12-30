package com.university.finance.pattern.command;

import java.util.Scanner;

import com.university.finance.model.Account;
import com.university.finance.service.BankingService;

// Commande pour effectuer un transfert entre deux comptes
public class TransferCommand implements Command {
    private BankingService bankingService;
    private Scanner scanner;
    
    // Constructeur avec injection de dépendances
    public TransferCommand(BankingService bankingService, Scanner scanner) {
        this.bankingService = bankingService;
        this.scanner = scanner;
    }
    
    // Exécute la commande de transfert
    @Override
    public void execute() {
        System.out.print("Nom utilisateur expéditeur: ");
        String fromUsername = scanner.nextLine();
        
        Account fromAccount = bankingService.getAccount(fromUsername);
        if (fromAccount != null) {
            System.out.print("Nom utilisateur destinataire: ");
            String toUsername = scanner.nextLine();
            
            Account toAccount = bankingService.getAccount(toUsername);
            if (toAccount != null) {
                System.out.print("Montant à transférer: ");
                try {
                    double amount = Double.parseDouble(scanner.nextLine());
                    if (amount > 0) {
                        double fromBalance = bankingService.getBalance(fromUsername);
                        if (fromBalance >= amount) {
                            bankingService.transfer(fromUsername, toUsername, amount);
                            System.out.println("Transfert réussi.");
                            System.out.println("Solde expéditeur: $" + bankingService.getBalance(fromUsername));
                            System.out.println("Solde destinataire: $" + bankingService.getBalance(toUsername));
                        } else {
                            System.out.println("Solde insuffisant. Solde actuel: $" + fromBalance);
                        }
                    } else {
                        System.out.println("Le montant doit être positif.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Montant invalide.");
                }
            } else {
                System.out.println("Compte destinataire non trouvé pour l'utilisateur: " + toUsername);
            }
        } else {
            System.out.println("Compte expéditeur non trouvé pour l'utilisateur: " + fromUsername);
        }
    }
}