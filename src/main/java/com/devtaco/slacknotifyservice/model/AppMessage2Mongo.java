package com.devtaco.slacknotifyservice.model;

import org.bson.types.ObjectId;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * mongo db 에는 msa 들이 보낸 로그들을 적재한다. <p>
 * 이를 위해 app message 를 상속해서 mongo 의 mobjectId 와 <p>
 * 수집시간 항목을 추가한다. 
 */
@Getter
@Setter
@NoArgsConstructor
public class AppMessage2Mongo extends AppMessage{
  /** document(mongo) db 에서 부여하는 uid */
  private ObjectId id;
  private Long collectEpoch;

  public AppMessage2Mongo(AppMessage appMessage) {
    super(appMessage);
    this.id = new ObjectId();
    this.collectEpoch = System.currentTimeMillis();
  }
  
}
