package com.devtaco.slacknotifyservice.repository;

import org.junit.ClassRule;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

public abstract class RepositoryIntegrationTest {

  @ClassRule
  public static final PostgreSQLContainer<?> POSTGRES_SQL_CONTAINER;

  static {
    POSTGRES_SQL_CONTAINER = new PostgreSQLContainer<>("postgres:latest")
        .withInitScript("test-container-init.sql");
        POSTGRES_SQL_CONTAINER.start();
  }

  @DynamicPropertySource
  static void overrideTestProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", POSTGRES_SQL_CONTAINER::getJdbcUrl);
    registry.add("spring.datasource.username", POSTGRES_SQL_CONTAINER::getUsername);
    registry.add("spring.datasource.password", POSTGRES_SQL_CONTAINER::getPassword);
  }

}
