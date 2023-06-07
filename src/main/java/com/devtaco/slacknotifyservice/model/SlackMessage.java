package com.devtaco.slacknotifyservice.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Getter;

public class SlackMessage {
  /**
   * Slack 모델에는 Block, Section 등의 형태가 있음. 이 중 Block 형태를 클래스로 만듦<p>
   */
  @Getter
  @Builder
  public static class SlackMsgBlock {
    private String type;
    private SlackMsgType text;
    private List<SlackMsgType> fields;
  }

  /**
   * Slack 의 Block 에는 type 들이 존재하고, 이 type 을 선언해줘야함.
   */
  @Getter
  @Builder
  public static class SlackMsgType {
    private String type; // block type
    private String text; // content
  }

  public static List<SlackMsgBlock> createErrorSlackBlockMsg(AppMessage waveMsg) {
    // Header(제목)
    SlackMsgType headerBlock = SlackMsgType.builder()
        .type("plain_text")
        .text(waveMsg.getTitle())
        .build();
    // 내용
    SlackMsgType contentBlock = SlackMsgType.builder()
        .type("mrkdwn")
        .text(waveMsg.toErrorMessageBlockFormat()).build();

    // 이걸 block 으로 감싸서 formatting
    SlackMsgBlock slackFormat = SlackMsgBlock.builder()
        .type("header")
        .text(headerBlock)
        .build();
    SlackMsgBlock dividerFormat = SlackMsgBlock.builder()
        .type("divider")
        .build();
    SlackMsgBlock slackFormat2 = SlackMsgBlock.builder()
        .type("section")
        .text(contentBlock)
        .build();

    List<SlackMsgBlock> blocks = new ArrayList<>();
    blocks.add(slackFormat);
    blocks.add(dividerFormat);
    blocks.add(slackFormat2);

    return blocks;
  }

  public static List<SlackMsgBlock> createInfoSlackPlainMsg(AppMessage waveMsg) {
    // 내용
    SlackMsgType contentBlock = SlackMsgType.builder()
        .type("plain_text")
        .text(waveMsg.toInfoMessagePlainFormat()).build();

    // 이걸 block 으로 감싸서 formatting
    SlackMsgBlock slackFormat = SlackMsgBlock.builder()
        .type("section")
        .text(contentBlock)
        .build();

    List<SlackMsgBlock> blocks = new ArrayList<>();
    blocks.add(slackFormat);
    return blocks;
  }
}
