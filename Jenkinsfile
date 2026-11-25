pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('SonarQube & Tests') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    dir('backend') {
                        sh 'mvn clean verify org.sonarsource.scanner.maven:sonar-maven-plugin:sonar -Dsonar.projectKey=gestion-projets -Dsonar.projectName="Gestion Projets"'
                    }
                }
            }
        }

        stage('Build Images in Minikube') {
            steps {
                sh '''
                docker build -t gestion-projets-backend:latest ./backend
                docker build -t gestion-projets-frontend:latest ./frontend
                minikube image load gestion-projets-backend:latest
                minikube image load gestion-projets-frontend:latest
                '''
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                sh 'kubectl apply -f k8s/'
                sh 'kubectl rollout restart deployment/backend-deployment -n devops'
                sh 'kubectl rollout restart deployment/frontend-deployment -n devops'
            }
        }
    }
}
