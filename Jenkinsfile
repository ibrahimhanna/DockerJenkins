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

    stage('Test') {
        steps {
            sh './mvn clean test'
        }
    }

    stage('Package') {
        steps {
            sh './mvn clean package -DskipTests'
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
                docker compose down
                docker compose up -d
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