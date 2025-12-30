## Pattern commande
L’utilisation du pattern Command dans ce projet est inspirée d’un cas d’usage présenté sur le site Refactoring Guru, qui décrit un problème d’interface utilisateur similaire.
Ce pattern a été retenu afin de simuler le comportement de l’application avant la phase de tests, en isolant les actions utilisateur de leur implémentation concrète.

L'emetteur : Menu.java
Commande Interface : Command.java
Les classes commandes concretes : 
- AddUserCommand.java
- DepositCommand.java
- DisplayBalanceCommand.java
- ExitCommand.java
- TransactionCommand.java
- TransactionHistory.java
- TransferCommand.java
- WithdrawCommand.java