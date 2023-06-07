package com.devtaco.slacknotifyservice.repository;

import org.junit.ClassRule;
import org.testcontainers.containers.MongoDBContainer;

/**
 * SAVE 외에 다른 기능이 필요할 경우 아래 container를 사용해서 테스트 시나리오를 만든다.
 */
public abstract class MongoDbIntegrationTest {
  
  @ClassRule 
  public static final MongoDBContainer MONGO;

  static {
    MONGO = new MongoDBContainer("mongo:latest").withExposedPorts(27017);
  }
  
}
