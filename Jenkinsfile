pipeline {
agent any

environment {
    IMAGE_NAME = 'dockerjenkins'
    IMAGE_TAG = 'latest'
}

stages {

    stage('Checkout') {
        steps {
            checkout scm
        }
    }

 /*   stage('Test') {
        steps {
           sh '''
                    chmod +x mvnw
                    ./mvnw clean test
                '''
        }
    }*/

    stage('Package') {
        steps {
            sh '''
                    chmod +x mvnw
                    ./mvnw clean package -DskipTests
                '''
        }
    }

    stage('Docker Build') {
        steps {
            sh 'docker build -t ${IMAGE_NAME}:${IMAGE_TAG} .'
        }
    }

    stage('Deploy') {
        steps {
            sh '''
           

            echo "=== Docker version ==="
            docker --version
            docker compose version

            echo "=== Current directory ==="
            pwd
            ls -la

            echo "=== Docker Compose DOWN ==="
            docker compose down

            echo "=== Docker Compose UP ==="
            docker compose up -d

            echo "=== Containers ==="
            docker compose ps

            echo "=== Docker networks ==="
            docker network ls

            echo "=== App logs ==="
            docker logs --tail=100 dockerjenkins-spring-app
        
            '''
        }
    }

    stage('Git Push') {
        steps {
            withCredentials([
                usernamePassword(
                    credentialsId: 'github-credentials',
                    usernameVariable: 'ibrahimhanna',
                    passwordVariable: 'KJSgdg12@#?12'
                )
            ]) {
                sh '''
                    git add .

                    if git diff --cached --quiet; then
                        echo "No changes to commit."
                    else
                        git commit -m "Jenkins: update application"
                        git push https://${GIT_USERNAME}:${GIT_PASSWORD}@github.com/ibrahimhanna/DockerJenkins.git HEAD:main
                    fi
                '''
            }
        }
    }
}

post {
    success {
        echo 'Build, Docker deployment and Git push completed successfully.'
    }

    failure {
        echo 'Pipeline failed.'
    }
}

}