package com.university.finance.pattern.command;

import com.university.finance.service.BankingService;
import com.university.finance.model.User;
import com.university.finance.model.Account;
import java.util.Scanner;

// Commande pour ajouter un nouvel utilisateur au système
public class AddUserCommand implements Command {
    private BankingService bankingService;
    private Scanner scanner;
    
    // Constructeur avec injection de dépendances
    public AddUserCommand(BankingService bankingService, Scanner scanner) {
        this.bankingService = bankingService;
        this.scanner = scanner;
    }
    
    // Exécute la commande d'ajout d'utilisateur
    @Override
    public void execute() {
        System.out.print("Nom utilisateur: ");
        String username = scanner.nextLine();
        
        System.out.print("Mot de passe: ");
        String password = scanner.nextLine();
        
        // Vérifier si l'utilisateur existe déjà
        User existingUser = bankingService.getUser(username);
        if (existingUser != null) {
            System.out.println("Utilisateur déjà existant: " + username);
            return;
        }
        
        // Créer l'utilisateur
        User user = bankingService.createUser(username, password);
        
        System.out.print("Solde initial du compte: "); // Demander le solde initial du compte
        try {
            double initialBalance = Double.parseDouble(scanner.nextLine());
            if (initialBalance >= 0) {
                Account account = bankingService.createAccount(username, initialBalance);
                System.out.println("Utilisateur créé avec succès: " + username);
                System.out.println("Compte créé avec solde: $" + initialBalance);
            } else {
                System.out.println("Le solde initial doit être positif ou nul.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Solde initial invalide. Création de l'utilisateur sans compte.");
            System.out.println("Utilisateur créé avec succès: " + username);
        }
    }
}