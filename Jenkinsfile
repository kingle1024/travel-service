pipeline {
    agent any

    environment {
        // Gradle 실행 파일의 경로를 환경 변수로 설정
        GRADLE_HOME = '/opt/gradle/gradle-8.7/bin'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/kingle1024/travel-service.git'
            }
        }
        stage('Build') {
            steps {
                sh '$GRADLE_HOME/gradle build -x test' // Gradle을 사용한 빌드 테스트 생략
            }
        }
        stage('Deploy') {
            steps {
                sh '$GRADLE_HOME/gradle deploy' // Gradle을 사용한 배포
            }
        }
    }
}