package com.university.finance.pattern.observer;

// Observateur pour la journalisation des audits
// Enregistre toutes les opérations dans les logs d'audit
public class AuditLogger implements TransactionObserver {
    // Met à jour les logs d'audit avec les détails de la transaction
    @Override
    public void update(String transactionDetails) {
        System.out.println("[AUDIT] " + transactionDetails);
    }
}