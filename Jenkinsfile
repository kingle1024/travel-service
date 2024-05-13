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
                sh '''
                sudo cp /var/lib/jenkins/workspace/git-pipeline/build/libs/travel-service-0.0.1-SNAPSHOT-plain.jar /server/build/

                echo "PID Check..."

                CURRENT_PID=$(ps -ef | grep java | grep travel-service* | awk \'{print $2}\')

                echo "Running PID: {$CURRENT_PID}"

                if [ -z $CURRENT_PID ]
                then
                	echo "Project is not running"
                else
                	sudo kill -9 $CURRENT_PID
                	sudo sleep 10
                fi

                echo "Deploy Project...."

                sudo nohup java -jar /server/build/travel-service-0.0.1-SNAPSHOT-plain.jar

                echo "Done"'''
            }
        }
    }
}