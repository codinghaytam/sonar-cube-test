## Le Pattern Observer 
Le pattern observer est un patterne de comportement qui notifie les observateur a chaque changement d'etant, dans un pattern observer, on a un sujet dont le role est de notifier tous les observateurs (dans notre cas : execution d'une transaction) et les observateurs execute une mise a jour de leur donnee en utilisant la methode update (dans notre cas les observateur : NotificationService.java et AuditLogger.java)
Interface Observer : TransactionObserver.java
Class concretes Observer : 
- AuditLogger.java
- NotificationService.java
Sujet ou Observable : BankingService.java