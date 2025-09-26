# 1️⃣ Build Stage
FROM gradle:8-jdk17 AS build
WORKDIR /app

# Gradle 캐시 활용을 위해 build.gradle, settings.gradle 먼저 복사
COPY build.gradle settings.gradle ./
COPY gradle/ gradle/

# 의존성만 먼저 다운로드 (테스트 제외)
RUN gradle build -x test --no-daemon || true

# 전체 소스 복사 후 빌드
COPY . .
RUN gradle build -x test --no-daemon

# 2️⃣ Package Stage
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app

# Build stage에서 생성된 JAR 복사
COPY --from=build /app/build/libs/*.jar app.jar

# 애플리케이션 실행
CMD ["java", "-jar", "app.jar"]
