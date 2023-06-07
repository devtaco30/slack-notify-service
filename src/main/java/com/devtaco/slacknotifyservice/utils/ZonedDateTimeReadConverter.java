package com.devtaco.slacknotifyservice.utils;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

public class ZonedDateTimeReadConverter implements Converter<Date, ZonedDateTime> {

  private ZoneOffset offset = ZoneOffset.UTC;

  @Override
  public ZonedDateTime convert(Date date) {
    return date.toInstant().atZone(offset);
  }
}