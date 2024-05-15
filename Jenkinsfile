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
                sh '$GRADLE_HOME/gradle build bootJar -x test' // Gradle을 사용한 빌드 테스트 생략
                sh 'sudo cp /var/lib/jenkins/workspace/git-pipeline/build/libs/travel-service-0.0.1-SNAPSHOT.jar /server/build/' // 빌드파일 복사
            }
        }
        stage('Deploy') {
            steps {
                sh '''
                sh /server/launcher/travel-service-shutdown.sh

                sudo sleep 10

                sh /server/launcher/travel-service-startup.sh

                echo "Done"'''
            }
        }
    }
}