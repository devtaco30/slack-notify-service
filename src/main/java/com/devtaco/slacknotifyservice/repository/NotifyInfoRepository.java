package com.devtaco.slacknotifyservice.repository;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.devtaco.slacknotifyservice.model.AppMessage2Mongo;

/**
 * Mongo DB Interface 가 정의 된다. <p>
 * 단일 database 에 단일 collection 을 사용한다.
 */
@Component
public class NotifyInfoRepository {

  private MongoTemplate mongoTemplate;

  private static final String WAVE_APP_MESSAGE_COLLECTION = "app-message";

  public NotifyInfoRepository(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  public List<AppMessage2Mongo> findAll() {
    Query query = Query.query(Criteria.where("collectEpoch").lt(ZonedDateTime.now()));
    return mongoTemplate.find(query, AppMessage2Mongo.class, WAVE_APP_MESSAGE_COLLECTION);
  }

  public void saveAppMessage(AppMessage2Mongo data) {
    mongoTemplate.insert(data, WAVE_APP_MESSAGE_COLLECTION);
  }

}
