package com.epam.mentoring.clientapp;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.lifecycle.Startables;
import org.testcontainers.utility.DockerImageName;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DirtiesContext
@Testcontainers
@ContextConfiguration(initializers = IntegrationTest.TestContainersInitializer.class)
public abstract class IntegrationTest {

    @Container
    public static MySQLContainer<?> mysql = new MySQLContainer<>(DockerImageName.parse("mysql:8"))
            .withDatabaseName("pizzeria");

    @Container
    public static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:6.2.0"));

    public static class TestContainersInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(@NotNull ConfigurableApplicationContext context) {
            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
                    context,
                    "spring.datasource.url=" + mysql.getJdbcUrl(),
                    "spring.datasource.username=" + mysql.getUsername(),
                    "spring.datasource.password=" + mysql.getPassword(),
                    "kafka.bootstrap-servers=" + kafka.getBootstrapServers()
            );
        }
    }

    @BeforeAll
    public static void beforeClass() {
        Startables.deepStart(Stream.of(mysql, kafka)).join(); // start containers in parallel
    }

    @AfterAll
    public static void afterClass() {
        mysql.stop();
        kafka.stop();
    }

    @Test
    public void containersTest() {
        assertTrue(mysql.isRunning());
        assertTrue(kafka.isRunning());
    }

}
