package com.devtaco.slacknotifyservice.utils;

import java.util.Collections;
import java.util.List;

import com.devtaco.slacknotifyservice.model.AppMessage;
import com.devtaco.slacknotifyservice.model.SlackMessage;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageConverter {

  private static ObjectMapper mapper = new ObjectMapper().setSerializationInclusion(Include.NON_NULL);

  public static AppMessage createAppMessage(String payloadFromKafka) {
    AppMessage slackMessageBlock = null;
    try {
      slackMessageBlock = mapper.readValue(payloadFromKafka, AppMessage.class);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return slackMessageBlock;
  }

  public static String createSlackMessageBlockString(AppMessage appMessage) {
    List<SlackMessage.SlackMsgBlock> msgBlock = Collections.EMPTY_LIST;
    if (appMessage.getExceptionMessage() == null) {
      // exception 발생 시에만 Block 형태로 보낸다.
      msgBlock = SlackMessage.createInfoSlackPlainMsg(appMessage);
    } else {
      msgBlock = SlackMessage.createErrorSlackBlockMsg(appMessage);
    }
    String formattedMessage = "";

    try {
      formattedMessage = mapper.writeValueAsString(msgBlock);
    } catch (JsonProcessingException e) {
      log.error("FAIL TO CREATE SLACK MESSAGE BLOCKS TO STRING", e);
    }

    return formattedMessage;
  }
}
