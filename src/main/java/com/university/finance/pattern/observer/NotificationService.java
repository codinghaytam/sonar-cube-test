package com.university.finance.pattern.observer;

// Service de notification pour informer les utilisateurs des opérations
// Affiche les notifications en temps réel lors des transactions
public class NotificationService implements TransactionObserver {
    // Affiche une notification avec les détails de la transaction
    @Override
    public void update(String transactionDetails) {
        System.out.println("[NOTIFICATION] " + transactionDetails);
        // Devops : On doit envoyer ici notification par email
    }
}