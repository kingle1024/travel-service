# 베이스 이미지 설정
FROM openjdk:11-jre-slim

# 작업 디렉토리 설정
WORKDIR /app

# JAR 파일 복사
COPY build/libs/*.jar app.jar
# 포트 설정 (예: 8080 포트)
EXPOSE 443

# 애플리케이션 실행
CMD ["java", "-jar", "app.jar"]
