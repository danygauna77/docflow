pipeline {
    agent any

    stages {

        stage('Compile') {
            steps {
                sh 'chmod +x gradlew'
                sh './gradlew clean classes'
            }
        }

        stage('Unit Tests') {
            steps {
                sh './gradlew test'
            }
        }

        stage('Package') {
            steps {
                sh './gradlew bootJar'
            }
        }
    }

    post {

        success {
            echo '✅ Build finalizado correctamente.'
        }

        failure {
            echo '❌ El build falló.'
        }

        always {
            junit 'build/test-results/test/*.xml'

            archiveArtifacts artifacts: 'build/libs/*.jar', fingerprint: true

            cleanWs()
        }
    }
}