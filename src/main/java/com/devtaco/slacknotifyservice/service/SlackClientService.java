package com.devtaco.slacknotifyservice.service;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.devtaco.slacknotifyservice.config.ConfigurationParamCenter;
import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;

/**
 * SlackClient 로 message 를 sending 하는 기능 <p>
 */
@Service
public class SlackClientService {

  private Slack slack;
  private MethodsClient client;
  private String slackBotToken;

  public SlackClientService(ConfigurationParamCenter paramCenter) {
    this.slack = Slack.getInstance();
    this.slackBotToken = paramCenter.getSlackBotToken();
  }

  @PostConstruct
  public void init() {
    this.client = slack.methods(this.slackBotToken);
  }

  public boolean sendMessage(String channelId, String message) throws IOException, SlackApiException {
    // channel id 와 block string 만 실어서 보내면 된다.
    ChatPostMessageRequest req = ChatPostMessageRequest.builder()
        .channel(channelId)
        .blocksAsString(message)
        .build();

    ChatPostMessageResponse res = this.client.chatPostMessage(req);

    if (!res.isOk()) {
      String errorMsg = res.getError();
      throw new RuntimeException(errorMsg);
    }

    return res.isOk();
  }

}
