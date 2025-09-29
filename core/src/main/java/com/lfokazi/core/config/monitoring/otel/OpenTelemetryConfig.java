package com.lfokazi.core.config.monitoring.otel;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.TracerProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenTelemetryConfig {
    @Bean
    OpenTelemetry openTelemetry() {
        return GlobalOpenTelemetry.get();
    }

    @Bean
    TracerProvider tracerProvider(OpenTelemetry openTelemetry) {
        return openTelemetry.getTracerProvider();
    }
}
