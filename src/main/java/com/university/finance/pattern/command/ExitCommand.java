package com.university.finance.pattern.command;

// Commande pour quitter l'application
public class ExitCommand implements Command {
    private MenuHandler menuHandler;
    
    // Constructeur avec injection de dépendances
    public ExitCommand(MenuHandler menuHandler) {
        this.menuHandler = menuHandler;
    }
    
    // Exécute la commande de sortie
    @Override
    public void execute() {
        System.out.println("Au revoir!");
        menuHandler.stop();
    }
}