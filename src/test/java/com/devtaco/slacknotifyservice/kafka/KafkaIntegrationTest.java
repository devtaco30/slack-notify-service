package com.devtaco.slacknotifyservice.kafka;

import static org.junit.Assert.assertTrue;

import org.junit.ClassRule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(initializers = {KafkaIntegrationTest.Initializer.class})
@Testcontainers
public abstract class KafkaIntegrationTest {

  @ClassRule
  public static final KafkaContainer KAFKA_CONTAINER;

  static {
    KAFKA_CONTAINER = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:6.2.1"));
    KAFKA_CONTAINER.start();
  }

  static class Initializer
      implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
      TestPropertyValues.of(
          "spring.kafka.bootstrapServers =" + KAFKA_CONTAINER.getBootstrapServers()).applyTo(applicationContext.getEnvironment());
    }
  }

  @Test
  public void test_container_run() {
    assertTrue(KAFKA_CONTAINER.isRunning());
  }
}
