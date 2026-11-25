# DevOps – Gestion des projets

Application de gestion d'entreprises, d'équipes et de projets, accompagnée d'une chaîne DevOps de conteneurisation, d'intégration continue, de déploiement Kubernetes et de supervision.

## Sommaire

- [Architecture](#architecture)
- [Arborescence du dépôt](#arborescence-du-dépôt)
- [Prérequis et ports](#prérequis-et-ports)
- [Démarrage avec Docker Compose](#démarrage-avec-docker-compose)
- [Déploiement sur Kubernetes avec Minikube](#déploiement-sur-kubernetes-avec-minikube)
- [Pipeline Jenkins, SonarQube et JaCoCo](#pipeline-jenkins-sonarqube-et-jacoco)
- [Monitoring Prometheus et Grafana](#monitoring-prometheus-et-grafana)
- [API REST](#api-rest)
- [Exécution locale](#exécution-locale)

## Architecture

```text
Navigateur
   ├── Angular 22 servi par Nginx (port hôte 4200)
   └── API Spring Boot (port 8080)
           └── MySQL 8.0, base test_db (port 3306)

Jenkins (8090) ── tests + JaCoCo ── SonarQube (9000)
       └── construction des images ── Minikube / Kubernetes (namespace devops)

Prometheus (9090) ── collecte /actuator/prometheus ── Grafana (3000)
```

- **Backend** : Java 17, Spring Boot 4.1.0, Spring Web MVC, Spring Data JPA et Hibernate. Actuator et Micrometer exposent les métriques Prometheus.
- **Frontend** : Angular 22, application monopage avec des vues pour les entreprises, les équipes, les projets et les projets détaillés.
- **Base de données** : MySQL 8.0. Adminer fournit une interface web d'administration.
- **Conteneurs** : les Dockerfiles du backend et du frontend utilisent chacun une construction multi-stage. Le backend est construit avec Maven puis exécuté sur une image JRE 17 ; le frontend est construit avec Node.js 22 puis servi par Nginx.
- **CI/CD** : le `Jenkinsfile` exécute la vérification Maven, les tests, JaCoCo et l'analyse SonarQube, construit les images et les déploie sur Minikube.
- **Orchestration** : les manifestes Kubernetes créent MySQL avec stockage persistant, le backend et le frontend dans le namespace `devops`.
- **Observabilité** : Prometheus collecte les métriques du backend et de Prometheus lui-même ; Grafana se connecte à Prometheus comme source de données.

> Jenkins et SonarQube ne sont pas déclarés dans le fichier Docker Compose de l'application. Ils doivent être disponibles séparément, sur les ports indiqués ci-dessous, et configurés pour le pipeline Jenkins.

## Arborescence du dépôt

Arborescence des fichiers du dépôt (les répertoires générés tels que `target/` et les dépendances installées ne sont pas inclus) :

```text
.
├── README.md
├── Jenkinsfile
├── docker-compose.yml
├── backend/
│   ├── .gitattributes
│   ├── .gitignore
│   ├── .mvn/wrapper/maven-wrapper.properties
│   ├── mvnw
│   ├── mvnw.cmd
│   ├── Dockerfile
│   ├── pom.xml
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/tn/esprit/backend/
│   │   │   │   ├── BackendApplication.java
│   │   │   │   ├── controller/
│   │   │   │   │   ├── EntrepriseController.java
│   │   │   │   │   ├── EquipeController.java
│   │   │   │   │   ├── ProjetController.java
│   │   │   │   │   └── ProjetDetailleController.java
│   │   │   │   ├── dto/
│   │   │   │   │   ├── EntrepriseDTO.java
│   │   │   │   │   ├── EquipeDTO.java
│   │   │   │   │   ├── ProjetDTO.java
│   │   │   │   │   └── ProjetDetailleDTO.java
│   │   │   │   ├── entity/
│   │   │   │   │   ├── Entreprise.java
│   │   │   │   │   ├── Equipe.java
│   │   │   │   │   ├── Projet.java
│   │   │   │   │   └── ProjetDetaille.java
│   │   │   │   ├── repository/
│   │   │   │   │   ├── EntrepriseRepository.java
│   │   │   │   │   ├── EquipeRepository.java
│   │   │   │   │   ├── ProjetRepository.java
│   │   │   │   │   └── ProjetDetailleRepository.java
│   │   │   │   └── service/
│   │   │   │       ├── IEntrepriseService.java
│   │   │   │       ├── IEquipeService.java
│   │   │   │       ├── IProjetService.java
│   │   │   │       ├── IProjetDetailleService.java
│   │   │   │       └── impl/
│   │   │   │           ├── EntrepriseServiceImpl.java
│   │   │   │           ├── EquipeServiceImpl.java
│   │   │   │           ├── ProjetServiceImpl.java
│   │   │   │           └── ProjetDetailleServiceImpl.java
│   │   │   └── resources/application.properties
│   │   └── test/java/tn/esprit/backend/
│   │       ├── BackendApplicationTests.java
│   │       ├── controller/
│   │       │   ├── EntrepriseControllerTest.java
│   │       │   ├── EquipeControllerTest.java
│   │       │   ├── ProjetControllerTest.java
│   │       │   └── ProjetDetailleControllerTest.java
│   │       └── dto/DtoTest.java
│   └── backend/                 # Copie imbriquée d'un ancien projet Spring Boot
│       ├── .gitattributes
│       ├── .gitignore
│       ├── .mvn/wrapper/maven-wrapper.properties
│       ├── mvnw
│       ├── mvnw.cmd
│       ├── pom.xml
│       └── src/
│           ├── main/
│           │   ├── java/tn/esprit/backend/
│           │   │   ├── BackendApplication.java
│           │   │   ├── controller/
│           │   │   │   ├── EntrepriseController.java
│           │   │   │   ├── EquipeController.java
│           │   │   │   ├── ProjetController.java
│           │   │   │   └── ProjetDetailleController.java
│           │   │   ├── entity/
│           │   │   │   ├── Entreprise.java
│           │   │   │   ├── Equipe.java
│           │   │   │   ├── Projet.java
│           │   │   │   └── ProjetDetaille.java
│           │   │   ├── repository/
│           │   │   │   ├── EntrepriseRepository.java
│           │   │   │   ├── EquipeRepository.java
│           │   │   │   ├── ProjetRepository.java
│           │   │   │   └── ProjetDetailleRepository.java
│           │   │   └── service/
│           │   │       ├── IEntrepriseService.java
│           │   │       ├── IEquipeService.java
│           │   │       ├── IProjetService.java
│           │   │       ├── IProjetDetailleService.java
│           │   │       └── impl/
│           │   │           ├── EntrepriseServiceImpl.java
│           │   │           ├── EquipeServiceImpl.java
│           │   │           ├── ProjetServiceImpl.java
│           │   │           └── ProjetDetailleServiceImpl.java
│           │   └── resources/application.properties
│           └── test/java/tn/esprit/backend/BackendApplicationTests.java
├── frontend/
│   ├── .editorconfig
│   ├── .gitignore
│   ├── .prettierrc
│   ├── .vscode/
│   │   ├── extensions.json
│   │   ├── launch.json
│   │   └── tasks.json
│   ├── angular.json
│   ├── package.json
│   ├── package-lock.json
│   ├── tsconfig.json
│   ├── tsconfig.app.json
│   ├── tsconfig.spec.json
│   ├── Dockerfile
│   ├── public/favicon.ico
│   └── src/
│       ├── index.html
│       ├── main.ts
│       ├── styles.css
│       └── app/
│           ├── app.config.ts
│           ├── app.css
│           ├── app.html
│           ├── app.spec.ts
│           ├── app.ts
│           ├── app.routes.ts
│           ├── components/navbar/
│           │   ├── navbar.css
│           │   ├── navbar.html
│           │   └── navbar.ts
│           ├── models/
│           │   ├── entreprise.model.ts
│           │   ├── equipe.model.ts
│           │   ├── projet.model.ts
│           │   └── projet-detaille.model.ts
│           ├── services/
│           │   ├── entreprise.service.ts
│           │   ├── equipe.service.ts
│           │   ├── projet.service.ts
│           │   └── projet-detaille.service.ts
│           └── pages/
│               ├── entreprises/
│               │   ├── entreprises.css
│               │   ├── entreprises.html
│               │   └── entreprises.ts
│               ├── equipes/
│               │   ├── equipes.css
│               │   ├── equipes.html
│               │   └── equipes.ts
│               ├── projets/
│               │   ├── projets.css
│               │   ├── projets.html
│               │   └── projets.ts
│               └── projets-detailles/
│                   ├── projets-detailles.css
│                   ├── projets-detailles.html
│                   └── projets-detailles.ts
├── k8s/
│   ├── backend.yaml
│   ├── frontend.yaml
│   └── mysql.yaml
└── monitoring/
    ├── docker-compose.monitoring.yml
    └── prometheus.yml
```

Le build, le Dockerfile, le `Jenkinsfile` et les manifestes Kubernetes utilisent le projet principal `backend/` et non la copie imbriquée `backend/backend/`.

## Prérequis et ports

Prérequis principaux : Docker avec le plugin Compose, Java 17 et Maven (ou le wrapper Maven), Node.js 22/npm pour le développement frontend, ainsi que Minikube et `kubectl` pour Kubernetes. Le pipeline Jenkins nécessite également l'accès à Docker, Minikube, `kubectl` et à un serveur SonarQube.

| Service | Port hôte | Détails |
|---|---:|---|
| Frontend | `4200` | Angular en développement ; en Docker, le port hôte `4200` est redirigé vers Nginx sur `80` |
| Backend REST | `8080` | Spring Boot |
| MySQL | `3306` | Base `test_db`, identifiant `root`, mot de passe `root` |
| Adminer | `8082` | Interface web ; serveur `db` depuis le réseau Compose |
| Jenkins | `8090` | Instance Jenkins à fournir séparément |
| SonarQube | `9000` | Instance SonarQube à fournir séparément |
| Prometheus | `9090` | Stack de monitoring Compose |
| Grafana | `3000` | Stack de monitoring Compose ; identifiants configurés `admin` / `admin` |

Les identifiants de base de données et Grafana sont des valeurs de démonstration ; changez-les avant tout usage exposé ou partagé.

## Démarrage avec Docker Compose

Depuis la racine du dépôt, construire et démarrer MySQL, Adminer, le backend et le frontend :

```bash
docker compose up -d --build
```

Accès :

- Application : [http://localhost:4200](http://localhost:4200)
- API : [http://localhost:8080](http://localhost:8080)
- Adminer : [http://localhost:8082](http://localhost:8082) — serveur `db`, utilisateur `root`, mot de passe `root`, base `test_db`
- MySQL : `localhost:3306`

Voir les conteneurs et journaux :

```bash
docker compose ps
docker compose logs -f backend
```

Arrêter les services sans supprimer les données persistées :

```bash
docker compose down
```

Le volume `mysql_data` conserve les données MySQL. Pour supprimer également les données persistées, utilisez `docker compose down -v`.

## Déploiement sur Kubernetes avec Minikube

Les manifestes définissent le namespace `devops`, mais ne créent pas le namespace lui-même. Démarrez Minikube puis créez le namespace :

```bash
minikube start
kubectl create namespace devops
```

Construisez les images depuis la racine du dépôt et chargez-les dans Minikube. Les noms doivent correspondre aux images des manifestes (`imagePullPolicy: Never`) :

```bash
docker build -t gestion-projets-backend:latest ./backend
docker build -t gestion-projets-frontend:latest ./frontend
minikube image load gestion-projets-backend:latest
minikube image load gestion-projets-frontend:latest
```

Déployez MySQL, le backend et le frontend :

```bash
kubectl apply -f k8s/
kubectl get pods,services -n devops
```

Pour accéder aux services en local, ouvrez chacun de ces port-forward dans un terminal séparé :

```bash
kubectl port-forward -n devops service/frontend-service 4200:80
```

```bash
kubectl port-forward -n devops service/backend-service 8080:8080
```

L'interface est alors disponible sur [http://localhost:4200](http://localhost:4200), et l'API sur [http://localhost:8080](http://localhost:8080). Le service backend expose aussi le NodePort `30080` et le frontend le NodePort `30082`.

Pour supprimer les ressources applicatives :

```bash
kubectl delete -f k8s/
kubectl delete namespace devops
```

## Pipeline Jenkins, SonarQube et JaCoCo

Le pipeline déclaratif est défini dans `Jenkinsfile`. Il enchaîne le checkout SCM, la vérification et les tests Maven, le rapport de couverture JaCoCo, l'analyse SonarQube, la construction/charge des images dans Minikube, puis le déploiement Kubernetes.

### Configuration requise

1. Installer Jenkins et les plugins Pipeline, Git et SonarQube Scanner for Jenkins.
2. Configurer dans Jenkins une installation SonarQube dont le nom est **`SonarQube`**, utilisé par `withSonarQubeEnv('SonarQube')` dans le pipeline. Renseigner l'URL de SonarQube et un jeton d'accès via la configuration Jenkins.
3. Créer un job Pipeline pointant vers le dépôt et utilisant le `Jenkinsfile` à la racine.
4. S'assurer que l'agent Jenkins dispose de Java 17, Maven, Docker, Minikube et `kubectl`, et qu'il peut accéder au cluster Minikube.
5. Créer le namespace avant le premier déploiement (`kubectl create namespace devops`) : les manifestes le ciblent, mais ne déclarent pas sa création.

### Étapes exécutées

L'étape d'analyse du pipeline lance l'équivalent de :

```bash
cd backend
mvn clean verify org.sonarsource.scanner.maven:sonar-maven-plugin:sonar \
  -Dsonar.projectKey=gestion-projets \
  -Dsonar.projectName="Gestion Projets"
```

`verify` exécute les tests et l'exécution JaCoCo configurée dans `backend/pom.xml`. Le rapport HTML et le rapport XML sont générés sous `backend/target/site/jacoco/` (notamment `index.html` et `jacoco.xml`), puis le scanner Maven transmet les résultats à SonarQube.

Ensuite, le `Jenkinsfile` construit les images `gestion-projets-backend:latest` et `gestion-projets-frontend:latest`, les charge avec `minikube image load`, applique `k8s/`, puis redémarre les déploiements. Les outils et le namespace doivent donc déjà être accessibles depuis l'agent Jenkins.

Pour exécuter manuellement les tests et générer JaCoCo sans lancer SonarQube :

```bash
cd backend
mvn clean verify
```

## Monitoring Prometheus et Grafana

L'application Spring expose le endpoint Prometheus grâce à Actuator et Micrometer. Démarrez le backend sur le port `8080`, par Docker Compose ou via le port-forward Kubernetes, puis démarrez le stack de monitoring depuis la racine :

```bash
docker compose -f monitoring/docker-compose.monitoring.yml up -d
```

Vérifiez que les métriques du backend répondent :

```bash
curl http://localhost:8080/actuator/prometheus
```

Interfaces :

- Prometheus : [http://localhost:9090](http://localhost:9090)
- Grafana : [http://localhost:3000](http://localhost:3000), identifiants `admin` / `admin`

La configuration `monitoring/prometheus.yml` scrape le backend à `host.docker.internal:8080/actuator/prometheus` toutes les 15 secondes. Le fichier Compose ajoute la résolution `host.docker.internal` vers l'hôte sur Linux. Dans Grafana, ajoutez Prometheus comme source de données avec l'URL **`http://prometheus:9090`**, puis créez ou importez un tableau de bord. Aucun tableau de bord Grafana n'est préprovisionné dans le dépôt. Une requête PromQL simple pour vérifier la collecte est :

```promql
up{job="spring-boot-backend"}
```

Pour arrêter Prometheus et Grafana :

```bash
docker compose -f monitoring/docker-compose.monitoring.yml down
```

Les volumes `prometheus_data` et `grafana_data` conservent leurs données. Le monitoring Kubernetes n'est pas déployé par les manifestes `k8s/` fournis.

## API REST

Les endpoints suivants sont implémentés par les contrôleurs Spring Boot. Sauf indication contraire, les routes de ressources acceptent les opérations CRUD listées ci-dessous.

| Ressource | Méthode | Endpoint | Description |
|---|---|---|---|
| Entreprise | `GET` | `/entreprise/all` | Lister les entreprises |
| Entreprise | `GET` | `/entreprise/get/{id}` | Lire une entreprise |
| Entreprise | `POST` | `/entreprise/add` | Créer une entreprise |
| Entreprise | `PUT` | `/entreprise/update` | Modifier une entreprise |
| Entreprise | `DELETE` | `/entreprise/delete/{id}` | Supprimer une entreprise |
| Équipe | `GET` | `/equipe/all` | Lister les équipes |
| Équipe | `GET` | `/equipe/get/{id}` | Lire une équipe |
| Équipe | `GET` | `/equipe/by-entreprise/{entrepriseId}` | Lister les équipes d'une entreprise |
| Équipe | `POST` | `/equipe/add` | Créer une équipe |
| Équipe | `PUT` | `/equipe/update` | Modifier une équipe |
| Équipe | `DELETE` | `/equipe/delete/{id}` | Supprimer une équipe |
| Équipe | `PUT` | `/equipe/assign-entreprise/{equipeId}/{entrepriseId}` | Affecter une équipe à une entreprise |
| Équipe | `PUT` | `/equipe/assign-projet/{equipeId}/{projetId}` | Affecter une équipe à un projet |
| Projet | `GET` | `/projet/all` | Lister les projets |
| Projet | `GET` | `/projet/get/{id}` | Lire un projet |
| Projet | `POST` | `/projet/add` | Créer un projet |
| Projet | `PUT` | `/projet/update` | Modifier un projet |
| Projet | `DELETE` | `/projet/delete/{id}` | Supprimer un projet |
| Projet détaillé | `GET` | `/projet-detaille/all` | Lister les projets détaillés |
| Projet détaillé | `GET` | `/projet-detaille/get/{id}` | Lire un projet détaillé |
| Projet détaillé | `GET` | `/projet-detaille/by-projet/{projetId}` | Lister les projets détaillés liés à un projet |
| Projet détaillé | `POST` | `/projet-detaille/add` | Créer un projet détaillé |
| Projet détaillé | `PUT` | `/projet-detaille/update` | Modifier un projet détaillé |
| Projet détaillé | `DELETE` | `/projet-detaille/delete/{id}` | Supprimer un projet détaillé |
| Projet détaillé | `PUT` | `/projet-detaille/assign-projet/{projetDetailleId}/{projetId}` | Affecter un projet détaillé à un projet |

Remplacez `{id}` et les paramètres de route entre accolades par leurs valeurs. Les opérations de création et de mise à jour attendent les objets correspondants dans le corps de la requête.

## Exécution locale

### Backend

La configuration locale par défaut attend MySQL sur `localhost:3306` avec la base et les identifiants indiqués dans le tableau des ports. Depuis la racine :

```bash
cd backend
mvn spring-boot:run
```

### Frontend

Dans un second terminal :

```bash
cd frontend
npm ci
npm start
```

L'application Angular est disponible sur [http://localhost:4200](http://localhost:4200) et ses services HTTP appellent l'API sur `http://localhost:8080`.
