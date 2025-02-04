# DS Architecture Logiciel

### 1. Modifier origine configuration du serveur :
Dans **config-server/src/main/resources/application.yml** modifier l'url.
```yml
        git:
            uri: <votre-repertoire-git>
            default-label: main
```

### 2. Kafka
N'oublier pas de lancer le [docker-compose.yml](/docker-compose.yml) contenant le Kafka permettant l'envoie de message entre les conteneurs.

### 3. Design pattern Choisie
**Chain of Responsibility**

J'ai utilisé le pattern Chain of Responsibility pour valider les utilisateurs de manière séquentielle. Ce design pattern rend le code plus modulaire et facile à améliorer. C'est pour ca que j'ai modifié [UserService.java](/user-service/src/main/java/ynov/architecture/ds/userservice/service/UserService.java).

### 4. Swagger
Vous pouvez accèder à Swagger via les URLs :
- http://localhost:8081/swagger-ui/index.html
- http://localhost:8082/swagger-ui/index.html
- http://localhost:8083/swagger-ui/index.html