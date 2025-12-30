import com.university.finance.pattern.command.MenuHandler;
import com.university.finance.pattern.factory.ConcreteAccountFactory;
import com.university.finance.pattern.factory.ConcreteUserFactory;
import com.university.finance.service.BankingService;
import com.university.finance.service.TransactionService;

import io.prometheus.client.exporter.HTTPServer;
import io.prometheus.client.hotspot.DefaultExports;

// Classe principale de l'application financière
// Point d'entrée de l'application avec initialisation de tous les composants
public class MainApp {
    // Méthode principale qui démarre l'application
    public static void main(String[] args) {
        System.out.println("=== Finance Application STARTING ===");
        
        // Try to initialize Prometheus metrics
        try {
            // Using reflection to check if Prometheus classes are available
            Class.forName("io.prometheus.client.hotspot.DefaultExports");
            Class.forName("io.prometheus.client.exporter.HTTPServer");
            
            // If classes are available, initialize Prometheus
            DefaultExports.initialize();
            HTTPServer server = new HTTPServer(8082);
            System.out.println("Metrics server started on port 8082");
        } catch (ClassNotFoundException e) {
            System.out.println("Prometheus libraries not found. Running without metrics.");
        } catch (Exception e) {
            System.err.println("Failed to start metrics server: " + e.getMessage());
        }
        
        // Initialiser les factories
        ConcreteAccountFactory accountFactory = new ConcreteAccountFactory();
        ConcreteUserFactory userFactory = new ConcreteUserFactory();
        
        // Initialiser les services    
        BankingService bankingService = new BankingService(accountFactory, userFactory, null); // Temporary null
        TransactionService transactionService = new TransactionService(bankingService);
        // S
        // et the transaction service in banking service using a setter
        bankingService.setTransactionService(transactionService);
        
        // Créer et exécuter le menu
        MenuHandler menuHandler = new MenuHandler(bankingService, transactionService);
        menuHandler.run();
        
        System.out.println("=== Finance Application ENDING ===");
    }
}