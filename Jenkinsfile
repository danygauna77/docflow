pipeline {
    agent any

    stages {

        stage('Build') {
            steps {
                sh 'chmod +x gradlew'
                sh './gradlew build'
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