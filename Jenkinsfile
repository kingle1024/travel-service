pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/kingle1024/travel-service.git'
            }
        }
        stage('Build') {
            steps {
                sh 'gradle clean build' // Gradle을 사용한 빌드
            }
        }
        stage('Test') {
            steps {
                sh 'gradle test' // Gradle을 사용한 테스트 실행
            }
        }
        stage('Deploy') {
            steps {
                sh 'gradle deploy' // Gradle을 사용한 배포
            }
        }
    }
}