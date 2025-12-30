# Application de Finance Refactorisée

Ceci est une version refactorisée de l'application Spaghetti Finance avec une architecture plus propre suivant les patrons de conception.

## Structure du Projet

```
refactored-finance-app/
├── src/main/java/com/university/finance/
│   ├── pattern/strategy/
│   │   ├── TransactionStrategy.java
│   │   ├── DepositStrategy.java
│   │   ├── WithdrawStrategy.java
│   │   └── TransferStrategy.java
│   ├── pattern/factory/
│   │   ├── AccountFactory.java
│   │   ├── ConcreteAccountFactory.java
│   │   ├── UserFactory.java
│   │   └── ConcreteUserFactory.java
│   ├── pattern/observer/
│   │   ├── TransactionObserver.java
│   │   ├── AuditLogger.java
│   │   └── NotificationService.java
│   ├── pattern/command/
│   │   ├── Command.java
│   │   ├── AddUserCommand.java
│   │   ├── DepositCommand.java
│   │   ├── DisplayBalanceCommand.java
│   │   ├── ExitCommand.java
│   │   ├── MenuHandler.java
│   │   ├── TransactionHistoryCommand.java
│   │   ├── TransferCommand.java
│   │   └── WithdrawCommand.java
│   ├── model/
│   │   ├── Account.java
│   │   ├── ConcreteAccount.java
│   │   ├── User.java
│   │   ├── ConcreteUser.java
│   │   └── Transaction.java
│   ├── service/
│   │   ├── BankingService.java
│   │   └── TransactionService.java
│   └── MainApp.java
├── src/test/java/
│   └── (tests unitaires et d'intégration)
├── pom.xml
├── Dockerfile
├── docker-compose.yml
├── Jenkinsfile
├── sonar-project.properties
├── prometheus/
│   └── prometheus.yml
└── README.md
```

## Patrons de Conception Requis

1. **Patron Stratégie**: Pour les différents types de transactions (dépôt, retrait, transfert)
   - Interface : `TransactionStrategy`
   - Implémentations concrètes : `DepositStrategy`, `WithdrawStrategy`, `TransferStrategy`
   - Contexte : `TransactionService`

2. **Patron Fabrique**: Pour la création de comptes et d'utilisateurs
   - Interfaces : `AccountFactory`, `UserFactory`
   - Implémentations concrètes : `ConcreteAccountFactory`, `ConcreteUserFactory`

3. **Patron Observateur**: Pour la surveillance des transactions et les notifications
   - Sujet : `BankingService`
   - Observateurs : `AuditLogger`, `NotificationService`


## Patrons de Conception Additionnel Utilisés

1. **Patron Commande**: Pour encapsuler les requêtes en objets
   - Interface : `Command`
   - Implémentations concrètes : `AddUserCommand`, `DepositCommand`, `WithdrawCommand`, etc.
   - Invocateur : `MenuHandler`

2. **Patron Singleton**: Pour assurer qu'une seule instance de configuration existe
   - Implémentation : `AppConfig`

## Livrables
- Refactoring des 4 patrons de conception (Stratégie, Singleton, Observateur, Fabrique) + Ajout d'un patron optionnel pour la gestion de menu en utilisant le patron Commande
- Conteneurisation (Application, Prometheus et Grafana)
- Configuration de Prometheus et Grafana (scraping et visualisation des métriques de performance de notre application Finance ainsi que les métriques de la JVM et d'autres métriques additionnels comme le nombre total d'utilisateurs et de transactions en utilisant Counter de Prometheus)
- (en cours) Tests JUnit
- (en cours) JaCoCo pour la couverture de code
- (en cours) SonarQube pour les métriques de qualité de code
- (en cours) Actions CI/CD par Jenkinsfile et automatisation d'envoi de notification par email.

## Guide d'installation

```bash
git clone https://github.com/LinaAitIder/refactored-finance-app

cd refactored-finance-app
```

## Exécution de l'application

### En mode développement (ligne de commande)

```bash
cd src/main/java/com/university/finance
```

En utilisant votre éditeur, exécutez le fichier MainApp.java

### En mode conteneurisé

Pour tester la conteneurisation de l'application, à la racine du dépôt où se trouve le fichier docker-compose.yml, exécutez :

```bash
docker-compose up
```

Vérifier que les ports des conteneurs ne sont pas utilisés par d'autres processus avant l'exécution de cette commande

## Accès aux services 
Prometheus : http://localhost:9090
Grafana : http://localhost:3000 (identifiant par défaut : admin/admin)

Application n'est pas une application web mais a été conteneurisée en raison du scraping des métriques de Prometheus, ayant besoin que Prometheus communique avec notre application dans l'environnement Docker

## Construction et Exécution

Ce projet utilise Maven pour la gestion des dépendances et l'automatisation de la construction.

### Prérequis

- Java 17 ou supérieur
- Maven 3.6 ou supérieur
- Docker (optionnel, pour exécuter avec conteneur)

### Construction du projet

```bash
mvn clean package
```

### Exécution de l'application

#### En local

```bash
java -jar target/spaghetti-finance-1.0.0.jar
```

#### Avec Docker

```bash
docker build -t refactored-finance-app .
docker run -p 8083:8083 refactored-finance-app
```

### Accès aux métriques Prometheus

Les métriques sont exposées sur le port 8082 :
- Endpoint : `http://localhost:8083/metrics`
- Les métriques incluent :
  - Nombre total d'utilisateurs (`finance_users_total`)
  - Nombre total de comptes (`finance_accounts_total`)
  - Nombre total de transactions (`finance_transactions_total`)

# Visualisation Graphana 

<img width="1398" height="829" alt="Image" src="https://github.com/user-attachments/assets/83187bb3-7892-4014-96f3-e0167d629704" />
<img width="1911" height="952" alt="Image" src="https://github.com/user-attachments/assets/c259824c-d934-46a9-b08c-73b3fb4ff665" />


# Diagramme UML
(en cours ...)
