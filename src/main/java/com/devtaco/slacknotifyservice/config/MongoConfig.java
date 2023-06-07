package com.devtaco.slacknotifyservice.config;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import com.devtaco.slacknotifyservice.utils.ZonedDateTimeReadConverter;
import com.devtaco.slacknotifyservice.utils.ZonedDateTimeWriteConverter;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import lombok.Getter;

@Configuration
@EnableMongoAuditing
public class MongoConfig {

  @Value("${spring.profiles.active}")
  private String env;

  private String mongoDBName;
  private String mongoDBPath;
  private String mongoDBAdminName;
  private String mongoDBAdminPW;

  @Getter
  private MongoClient mongoClient;

  public MongoConfig(ConfigurationParamCenter configParams) {
    this.mongoDBPath = configParams.getMongoDbPath();
    this.mongoDBAdminName = configParams.getMongoDbAdminName();
    this.mongoDBAdminPW = configParams.getMongoDbAdminPassword();
    this.mongoDBName = configParams.getMongoDbName();
  }

  /**
   * ZonedDateTime 을 Date (doc db가 사용하는 형태)로 convert 하기 위한 converter를 붙인다.
   * @return
   */
  @Bean
  public MongoCustomConversions customConversions() {
    List<Converter<?, ?>> converters = new ArrayList<>();
    converters.add(new ZonedDateTimeReadConverter());
    converters.add(new ZonedDateTimeWriteConverter());
    return new MongoCustomConversions(converters);
  }

  /**
   * mongoClient 를 그냥 쓰거나, default mongoTemplate 를 사용해도 되지만 ,<p>
   * converter를 붙이기 위해 별도 bean 을 생성한다. 
   * @return
   */
  @Bean
  public MongoTemplate mongoTemplate() {
    this.mongoClient = MongoClients.create(createMongoSettings());
    MongoDatabaseFactory factory = new SimpleMongoClientDatabaseFactory(this.mongoClient, this.mongoDBName);
    MappingMongoConverter converter = new MappingMongoConverter(new DefaultDbRefResolver(factory),
        new MongoMappingContext());
    converter.setCustomConversions(customConversions());
    converter.setTypeMapper(new DefaultMongoTypeMapper(null));
    converter.afterPropertiesSet();

    return new MongoTemplate(factory, converter);
  }

  /** 
   * 서비스 환경에서 사용하기 위해선 mongo 에 SSL 을 적용해야 할 것이다. <p>
   * 여기엔 그 설정이 없다.
   */
  public MongoClientSettings createMongoSettings() {

    CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
    CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);

    return MongoClientSettings.builder()
        .applyToClusterSettings(builder -> builder.hosts(Arrays.asList(new ServerAddress(mongoDBPath, 27017))))
        .readPreference(ReadPreference.secondaryPreferred())
        .retryWrites(false)
        .credential(MongoCredential.createCredential(mongoDBAdminName, mongoDBName,
            mongoDBAdminPW.toCharArray()))
        .applyToConnectionPoolSettings(builder -> builder.maxSize(10))
        .applyToConnectionPoolSettings(builder -> builder.maxConnectionIdleTime(10, TimeUnit.MINUTES))
        .applyToConnectionPoolSettings(builder -> builder.maxWaitTime(2, TimeUnit.MINUTES))
        .applyToClusterSettings(builder -> builder.serverSelectionTimeout(30, TimeUnit.SECONDS))
        .applyToSocketSettings(builder -> builder.connectTimeout(10, TimeUnit.SECONDS))
        .applyToSocketSettings(builder -> builder.readTimeout(0, TimeUnit.SECONDS))
        .codecRegistry(codecRegistry)
        .build();
  }

}
