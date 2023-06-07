package com.devtaco.slacknotifyservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(
  classes = { SlackClientService.class}
)
public class SlackClientServiceTest {

  @Value("${devtaco.app.slackBotToken}")
  String slackBotToken;

  @Autowired
  SlackClientService slackClientService;

  // TODO: write somthing test...

}
