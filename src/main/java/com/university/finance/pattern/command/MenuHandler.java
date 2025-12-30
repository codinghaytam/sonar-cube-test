package com.university.finance.pattern.command;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.university.finance.service.BankingService;
import com.university.finance.service.TransactionService;

// Gestionnaire de menu qui coordonne l'exécution des commandes
public class MenuHandler {
    private BankingService bankingService;
    private TransactionService transactionService;
    private Scanner scanner;
    private Map<Integer, Command> commands;
    private boolean running;

    // Constructeur avec injection de dépendances
    public MenuHandler(BankingService bankingService, TransactionService transactionService) {
        this.bankingService = bankingService;
        this.transactionService = transactionService;
        this.scanner = new Scanner(System.in);
        this.commands = new HashMap<>();
        this.running = true;
        initializeCommands();
    }

    // Initialise toutes les commandes disponibles
    private void initializeCommands() {
        commands.put(1, new DisplayBalanceCommand(bankingService, scanner));
        commands.put(2, new DepositCommand(bankingService, scanner));
        commands.put(3, new WithdrawCommand(bankingService, scanner));
        commands.put(4, new TransferCommand(bankingService, scanner));
        commands.put(5, new TransactionHistoryCommand(bankingService, scanner));
        commands.put(6, new AddUserCommand(bankingService, scanner));
        commands.put(0, new ExitCommand(this));
    }

    // Affiche le menu principal
    public void displayMenu() {
        System.out.println("\n===================================");
        System.out.println("=== Système Bancaire Refactorisé ===");
        System.out.println("===================================");
        System.out.println("1. Afficher solde");
        System.out.println("2. Déposer argent");
        System.out.println("3. Retirer argent");
        System.out.println("4. Transfert");
        System.out.println("5. Historique des transactions");
        System.out.println("6. Ajouter utilisateur");
        System.out.println("0. Quitter");
        System.out.print("Votre choix: ");
    }

    // Boucle principale de l'application
    public void run() {
        while (running) {
            displayMenu();
            try {
                String input = scanner.nextLine();
                if (input == null || input.trim().isEmpty()) {
                    System.out.println("Choix invalide. Veuillez réessayer.");
                    continue;
                }
                
                int choice = Integer.parseInt(input.trim());
                Command command = commands.get(choice);
                if (command != null) {
                    command.execute();
                } else {
                    System.out.println("Choix invalide. Veuillez réessayer.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrer un nombre valide.");
            } catch (Exception e) {
                System.out.println("Une erreur s'est produite: " + e.getMessage());
            }
        }
    }

    // Arrête l'application
    public void stop() {
        running = false;
    }
}