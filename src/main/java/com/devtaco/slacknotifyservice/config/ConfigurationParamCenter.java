package com.devtaco.slacknotifyservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

/**
 * Service 에 필요한 Config Parameter 들을 받습니다. (from yaml)
 */
@Configuration
@ConfigurationProperties(prefix = "devtaco.app")
@Getter
@Setter
public class ConfigurationParamCenter {
  // mongodb
  String mongoDbPath;
  String mongoDbName;
  String mongoDbAdminName;
  String mongoDbAdminPassword;
  String trustStorePassword;

  // slack info
  String slackBotToken;
  // kafka info
  String kafkaSntTopic;
  String kafktaSntGroup;

}
