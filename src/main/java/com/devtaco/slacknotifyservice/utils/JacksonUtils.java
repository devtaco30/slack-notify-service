package com.devtaco.slacknotifyservice.utils;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JacksonUtils {

  private static final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

  public static JsonFactory getJParser() {
    return mapper.getFactory();
  }

  public static JsonNode stringConvt2JsonNode(String value) throws JsonProcessingException {
    return mapper.readTree(value);
  }

  public static <T> String convtToString(T data) throws JsonProcessingException{
    return  mapper.writeValueAsString(data);
  }

  

}
