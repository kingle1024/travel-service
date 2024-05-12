pipeline {
    agent any

    environment {
        // Gradle 실행 파일의 경로를 환경 변수로 설정
        GRADLE_HOME = '/opt/gradle/gradle-{version}/bin'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/kingle1024/travel-service.git'
            }
        }
        stage('Build') {
            steps {
                sh '$GRADLE_HOME/gradle clean build' // Gradle을 사용한 빌드
            }
        }
        stage('Test') {
            steps {
                sh '$GRADLE_HOME/gradle test' // Gradle을 사용한 테스트 실행
            }
        }
        stage('Deploy') {
            steps {
                sh '$GRADLE_HOME/gradle deploy' // Gradle을 사용한 배포
            }
        }
    }
}