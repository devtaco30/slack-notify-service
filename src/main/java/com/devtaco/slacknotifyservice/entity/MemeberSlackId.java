package com.devtaco.slacknotifyservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * tb_member_slack_id 1:1 대응 entity
 * <p>
 * memberId 와 slackId 를 매칭한다.
 */
@Getter
@Setter
@Entity(name = "tb_member_slack_id")
@Table(name = "tb_member_slack_id")
public class MemeberSlackId {

  @Id
  @GeneratedValue
  @Column(name = "member_slack_id_seq")
  private long id;

  @Column(name = "member_id")
  private long memeberId;

  @Column
  private String memberSlackId;

  @Column
  private long regDt;

  @Column
  private boolean isDelete;

}
