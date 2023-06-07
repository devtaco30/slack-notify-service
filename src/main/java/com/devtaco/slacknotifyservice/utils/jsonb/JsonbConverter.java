package com.devtaco.slacknotifyservice.utils.jsonb;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.springframework.core.GenericTypeResolver;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

@Converter
public class JsonbConverter<T> implements AttributeConverter<T, String> {

  protected final ObjectMapper mapper;

  public JsonbConverter() {
    this.mapper = new ObjectMapper();
  }

  @Override
  public String convertToDatabaseColumn(T attribute) {

    if (ObjectUtils.isEmpty(attribute)) {
      return null;
    }
    try {
      return mapper.writeValueAsString(attribute);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public T convertToEntityAttribute(String dbData) {
    if (StringUtils.hasText(dbData)) {
      Class<?> classType = GenericTypeResolver.resolveTypeArgument(getClass(), JsonbConverter.class);
      try {
        return (T) mapper.readValue(dbData, classType);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
    return null;

  }

}
