# OpenJDK 17 이미지를 기반으로 합니다.
FROM openjdk:17-jdk-slim-buster

# JAR 파일의 경로를 인자로 받습니다.
ARG JAR_FILE=build/libs/*.jar

# 컨테이너 내 작업 디렉토리를 설정합니다.
WORKDIR /app

# 빌드된 JAR 파일을 app.jar라는 이름으로 컨테이너에 복사합니다.
COPY ${JAR_FILE} app.jar/

# 애플리케이션이 사용할 포트를 노출합니다.
EXPOSE 8083

# 컨테이너가 시작될 때 JAR 파일을 실행합니다.
ENTRYPOINT ["java","-jar","app.jar"]
