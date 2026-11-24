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
                eval $(minikube docker-env)
                DOCKER_BUILDKIT=0 docker build -t gestion-projets-backend:latest ./backend
                DOCKER_BUILDKIT=0 docker build -t gestion-projets-frontend:latest ./frontend
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
