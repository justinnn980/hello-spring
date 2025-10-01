# 1단계: 빌드
FROM gradle:8.7-jdk17 AS builder
WORKDIR /app
COPY . .
RUN gradle bootJar --no-daemon

# 2단계: 실행
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar

# Render는 PORT 환경변수를 자동으로 설정해줌
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
