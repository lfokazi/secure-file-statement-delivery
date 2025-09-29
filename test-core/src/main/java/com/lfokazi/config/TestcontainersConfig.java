package com.lfokazi.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.containers.localstack.LocalStackContainer.Service;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
public class TestcontainersConfig {
    @Bean
    @ServiceConnection
    PostgreSQLContainer<?> postgresContainer() {
        return new PostgreSQLContainer<>(DockerImageName.parse("postgres:16-alpine"))
                .withDatabaseName("accountsdb")
                .withInitScript("sql/init-test-data.sql")
                .withReuse(false);
    }

    @Bean
    @ServiceConnection
    LocalStackContainer localStackContainer() {
        return new LocalStackContainer(DockerImageName.parse("localstack/localstack:3.0"))
                .withServices(Service.S3)
                .withEnv("DEFAULT_REGION", "af-south-1")
                .withEnv("AWS_DEFAULT_REGION", "af-south-1")
                .withReuse(true);
    }
}
