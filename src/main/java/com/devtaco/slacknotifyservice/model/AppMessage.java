package com.devtaco.slacknotifyservice.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Application Message format 이라고 보면 된다.
 * <p>
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppMessage {

  private String projectName; // MSA 이름
  private String env; // 환경
  private List<String> toEmailList; // member email list (MSA 로부터 받는다)
  private List<String> toSlackIdList; // member slack id (넘겨받은 Email로 조회해서 set)
  private String slackChannelId; // slack channel destination (MSA 로부터 받는다)
  private String title;
  private String exceptionMessage; // exception 이 발생했을 경우 해당 부분이 null 이 아니게 온다 -> 포맷형태가 달라진다.
  private String message;

  /**
   * 상속받은 객체에서 사용하게 하기위해 자기 자신을 받아서 set 하는 constructor 를 만들어 둔다.
   * @param appMessage
   */
  public AppMessage(AppMessage appMessage) {
    this.projectName = appMessage.getProjectName();
    this.env = appMessage.getEnv();
    this.toEmailList = appMessage.getToEmailList();
    this.toSlackIdList = appMessage.getToSlackIdList();
    this.slackChannelId = appMessage.getSlackChannelId();
    this.title = appMessage.getTitle();
    this.exceptionMessage = appMessage.getExceptionMessage();
    this.message = appMessage.getMessage();
  }

  /**
   * error(exception) 일 경우엔 이 함수를 통해 block 이 만들어진다.
   * 
   * @return
   */
  public String toErrorMessageBlockFormat() {
    StringBuilder builder = new StringBuilder();
    builder.append("to: ");
    for (int i = 0; i < toSlackIdList.size(); i++) {
      builder.append("<").append(toSlackIdList.get(i)).append(">");
      if (i < toSlackIdList.size()) {
        builder.append(",");
      }
    }
    builder.append("\n");
    builder.append("project: ").append(projectName).append("\n");
    builder.append("env: ").append(env.toUpperCase()).append("\n");
    if (exceptionMessage != null && !exceptionMessage.isEmpty()) {
      builder.append("exceptionMessage: ").append(exceptionMessage).append("\n");
    }
    builder.append("message: ").append(message);
    return builder.toString();
  }

  /**
   * 단순 info 일 경우엔 이 함수를 통해 block 이 만들어진다.
   * 
   * @return
   */
  public String toInfoMessagePlainFormat() {
    StringBuilder builder = new StringBuilder();
    builder.append("project: ").append(projectName);
    builder.append(" | env: ").append(env.toUpperCase()).append("\n");
    builder.append(" message: ").append(message);
    return builder.toString();
  }

}
