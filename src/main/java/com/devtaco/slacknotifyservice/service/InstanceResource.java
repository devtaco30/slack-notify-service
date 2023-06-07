package com.devtaco.slacknotifyservice.service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * status 체크를 위한 api <p>
 * service 로 일단 둔다...
 */
@RestController
@RequestMapping(value = "/v1/instance")
public class InstanceResource {

  @GetMapping("/ping")
  public String statusCheck() {
    return "pong";
  }
  
}
