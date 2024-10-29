# # 베이스 이미지 설정
# FROM openjdk:11-jre-slim

# # 작업 디렉토리 설정
# WORKDIR /app

# # JAR 파일 복사
# COPY build/libs/*.jar app.jar
# # 포트 설정 (예: 8080 포트)
# EXPOSE 443

# # 애플리케이션 실행
# CMD ["java", "-jar", "app.jar"]

# jar 파일 빌드
FROM openjdk:11-jre-slim as builder

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
RUN chmod +x ./gradlew
RUN ./gradlew bootjar



# jar 실행
# 빌드를 하지 않으므로 JDK가 아닌 JRE를 베이스 이미지로 세팅
FROM openjdk:11-jre-slim as runtime

RUN addgroup --system --gid 1000 worker
RUN adduser --system --uid 1000 --ingroup worker --disabled-password worker
USER worker:worker

COPY --from=builder build/libs/*.jar app.jar

ENV PROFILE ${PROFILE}

EXPOSE 8080

ENTRYPOINT ["java", "-Dspring.profiles.active=${PROFILE}", "-jar", "/app.jar"]
