## 1단계: 빌드
#FROM gradle:8.7-jdk17 AS builder
#WORKDIR /app
#COPY . .
#RUN gradle bootJar --no-daemon
#
## 2단계: 실행
#FROM eclipse-temurin:17-jdk
#WORKDIR /app
#COPY --from=builder /app/build/libs/*.jar app.jar
#
## Render는 PORT env를 자동으로 넣어줌
#EXPOSE 8080
#
#ENTRYPOINT ["java", "-jar", "app.jar"]

# 1단계: 빌드
FROM gradle:8.7-jdk21 AS builder
WORKDIR /app

COPY gradlew .
COPY gradle gradle
COPY settings.gradle.kts .
COPY build.gradle.kts .
COPY gradle.properties .

RUN chmod +x ./gradlew

# 소스 복사
COPY . .

# 앱 모듈만 bootJar 생성 (테스트 제외)
RUN ./gradlew :apps:dama-api:bootJar --no-daemon -x test


# 2단계: 실행 (Amazon Corretto 21)
FROM amazoncorretto:21
WORKDIR /app

COPY --from=builder /app/apps/dama-api/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
