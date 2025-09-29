package com.lfokazi.core.config.monitoring;

import io.micrometer.core.aop.CountedAspect;
import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.instrumentation.micrometer.v1_5.OpenTelemetryMeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricsConfig {
    // overrides autoconfigured MeterRegistry bean with OpenTelemetry's MeterRegistry impl
    @Bean
    MeterRegistry meterRegistry(OpenTelemetry openTelemetry) {
        return OpenTelemetryMeterRegistry.builder(openTelemetry)
                .build();
    }

    @Bean
    TimedAspect timedAspect(MeterRegistry registry) {
        return new TimedAspect(registry);
    }

    @Bean
    CountedAspect countedAspect(MeterRegistry registry) {
        return new CountedAspect(registry);
    }
}
