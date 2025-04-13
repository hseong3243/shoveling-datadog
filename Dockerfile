# 베이스 이미지로 Java를 사용합니다
FROM eclipse-temurin:17-jdk

# 작업 디렉토리 설정
WORKDIR /app

# Datadog Java Agent 다운로드
RUN mkdir -p /opt/datadog && \
    curl -L -o /opt/datadog/dd-java-agent.jar https://dtdg.co/latest-java-tracer

# 인텔리제이 IDEA 기준 빌드 결과물 경로에서 JAR 파일 복사
# Maven 기준
#COPY ./target/*.jar app.jar
# Gradle을 사용하는 경우 아래 줄의 주석을 해제하고 위 줄을 주석 처리하세요
COPY ./build/libs/*.jar app.jar

# 환경 변수 설정
ENV JAVA_OPTS=""
ENV DD_SERVICE="spring-app"
ENV DD_ENV="dev"
ENV DD_VERSION="1.0"
ENV DD_AGENT_HOST="datadog-agent"
ENV DD_TRACE_AGENT_PORT="8126"
ENV DD_LOGS_INJECTION="true"
ENV DD_TRACE_OTEL_ENABLED="true"

LABEL com.datadoghq.tags.env="dev"
LABEL com.datadoghq.tags.service="spring-app"
LABEL com.datadoghq.tags.version="1.0"

# 8080 포트 노출
EXPOSE 8080

# 애플리케이션 실행
ENTRYPOINT ["java", \
            "-javaagent:/opt/datadog/dd-java-agent.jar", \
            "-jar", "/app/app.jar"]
