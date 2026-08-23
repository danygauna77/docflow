pipeline {
    agent any

    options {
        skipDefaultCheckout(true)
        timestamps()
        disableConcurrentBuilds()
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
                sh 'chmod +x gradlew'
            }
        }

        stage('Compile') {
            steps {
                sh './gradlew clean classes'
            }
        }

        stage('CheckStyle') {
            steps {
                sh './gradlew checkstyleMain'
            }
        }

        stage('SpotBugs') {
            steps {
                sh './gradlew spotbugsMain'
            }
        }

        stage('PMD') {
            steps {
                sh './gradlew pmdMain'
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

            script {
                currentBuild.displayName = "#${env.BUILD_NUMBER} - ${env.BRANCH_NAME}"
            }

            junit 'build/test-results/test/*.xml'

            archiveArtifacts artifacts: 'build/libs/*.jar', fingerprint: true

            archiveArtifacts artifacts: 'build/reports/checkstyle/**/*.*', fingerprint: true

            archiveArtifacts artifacts: 'build/reports/spotbugs/**/*.*', fingerprint: true

            archiveArtifacts artifacts: 'build/reports/pmd/**/*.*', fingerprint: true

            archiveArtifacts artifacts: 'build/reports/jacoco/**/*.*', fingerprint: true

            cleanWs()
        }
    }
}