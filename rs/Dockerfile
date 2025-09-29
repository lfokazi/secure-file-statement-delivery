FROM eclipse-temurin:17.0.5_8-jdk-focal as builder
WORKDIR extracted
ADD ./build/libs/*.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract

FROM eclipse-temurin:17.0.5_8-jre-focal
WORKDIR application
COPY --from=builder extracted/dependencies/ ./
COPY --from=builder extracted/spring-boot-loader/ ./
COPY --from=builder extracted/snapshot-dependencies/ ./
COPY --from=builder extracted/application/ ./

RUN apt-get update && apt-get install -y jq

ENV JAVA_TOOL_OPTIONS=" -javaagent:/application/BOOT-INF/lib/opentelemetry-javaagent-2.10.0.jar" \
    OTEL_SDK_DISABLED=false \
    OTEL_INSTRUMENTATION_COMMON_DEFAULT_ENABLED=true \
    OTEL_METRICS_EXPORTER=$OTEL_METRICS_EXPORTER \
    OTEL_LOGS_EXPORTER=$OTEL_LOGS_EXPORTER \
    OTEL_TRACES_EXPORTER=$OTEL_TRACES_EXPORTER \
    OTEL_TRACES_SAMPLER=parentbased_traceidratio \
    OTEL_TRACES_SAMPLER_ARG=1 \
    OTEL_EXPORTER_OTLP_METRICS_PROTOCOL=http/protobuf \
    OTEL_EXPORTER_OTLP_METRICS_ENDPOINT=$OTEL_EXPORTER_OTLP_METRICS_ENDPOINT \
    OTEL_EXPORTER_OTLP_TRACES_PROTOCOL=http/protobuf \
    OTEL_EXPORTER_OTLP_TRACES_ENDPOINT=$OTEL_EXPORTER_OTLP_TRACES_ENDPOINT \
    OTEL_EXPORTER_OTLP_LOGS_PROTOCOL=http/protobuf \
    OTEL_EXPORTER_OTLP_LOGS_ENDPOINT=$OTEL_EXPORTER_OTLP_LOGS_ENDPOINT \
    OTEL_RESOURCE_ATTRIBUTES="service.name=file-delivery-service,service.version=1.0.0,deployment.environment=local" \
    OTEL_INSTRUMENTATION_HIBERNATE_ENABLED=false \
    OTEL_INSTRUMENTATION_SPRING_DATA_ENABLED=false \
    OTEL_INSTRUMENTATION_LETTUCE_ENABLED=false

ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]

# Health check
HEALTHCHECK --interval=1m --timeout=10s --retries=3 --start-period=1m \
    CMD curl --fail --silent http://localhost:8080/actuator/health 2>&1 | \
    jq -r '.status' | grep -q 'UP' || exit 1