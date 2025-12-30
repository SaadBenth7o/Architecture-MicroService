# Architecture Microservices - TP

**Auteur:** Bendahou Saad

## Description

Ce projet implémente une architecture microservices basée sur Spring Boot et Spring Cloud, comprenant un service de découverte (Eureka), un Gateway API, et deux microservices (user-service et product-service).

## Structure du Projet

Le projet est composé de 4 services indépendants :

### 1. Discovery Service (Port 8761)
- **Rôle:** Service de découverte Eureka Server
- **Configuration:** 
  - Port: 8761
  - Ne s'enregistre pas lui-même (`register-with-eureka: false`)
  - Ne récupère pas le registre (`fetch-registry: false`)
- **Annotation:** `@EnableEurekaServer` pour activer le serveur Eureka

### 2. Gateway Service (Port 8080)
- **Rôle:** Point d'entrée unique pour toutes les requêtes client
- **Configuration:**
  - Port: 8080
  - Route `/users/**` → redirige vers `user-service`
  - Route `/produits/**` → redirige vers `product-service`
  - Utilise le load balancer (`lb://`) pour distribuer les requêtes
  - Se connecte à Eureka pour découvrir les services

### 3. User Service (Port 8084)
- **Rôle:** Microservice gérant les utilisateurs
- **Configuration:**
  - Port: 8084
  - Nom du service: `user-service`
  - S'enregistre auprès d'Eureka pour être découvert
  - Endpoint: `/users`

### 4. Product Service (Port 8083)
- **Rôle:** Microservice gérant les produits
- **Configuration:**
  - Port: 8083
  - Nom du service: `product-service`
  - S'enregistre auprès d'Eureka pour être découvert
  - Endpoint: `/produits`

## Explication du Code

### Discovery Service
Le service Discovery utilise Eureka Server pour maintenir un registre de tous les microservices actifs. Les autres services s'y enregistrent automatiquement et peuvent découvrir les autres services via ce registre central.

### Gateway Service
Le Gateway utilise Spring Cloud Gateway pour router les requêtes vers les microservices appropriés. La configuration dans `application.yml` définit les routes :
- Les routes utilisent `lb://` (load balancer) pour permettre la distribution de charge
- Les prédicats `Path` déterminent quelle route utiliser selon l'URL de la requête

### User Service et Product Service
Ces deux microservices sont des applications Spring Boot simples qui :
- Exposent des endpoints REST
- S'enregistrent automatiquement auprès d'Eureka lors du démarrage
- Peuvent être découverts et appelés via le Gateway

## Démarrage des Services

**Ordre de démarrage important :**

1. **Démarrer le Discovery Service** (port 8761)
   ```bash
   cd discovery-service
   mvn spring-boot:run
   ```

2. **Démarrer les microservices** (user-service et product-service)
   ```bash
   cd user-service
   mvn spring-boot:run
   
   cd product-service
   mvn spring-boot:run
   ```

3. **Démarrer le Gateway** (port 8080)
   ```bash
   cd gateway-service
   mvn spring-boot:run
   ```

## Tests

Une fois tous les services démarrés :

- Accéder au dashboard Eureka : http://localhost:8761
- Tester via le Gateway :
  - http://localhost:8080/users
  - http://localhost:8080/produits

## Technologies Utilisées

- Spring Boot 3.1.0
- Spring Cloud 2022.0.3
- Netflix Eureka (Service Discovery)
- Spring Cloud Gateway
- Java 17

