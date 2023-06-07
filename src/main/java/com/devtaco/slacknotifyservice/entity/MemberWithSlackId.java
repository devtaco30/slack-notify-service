package com.devtaco.slacknotifyservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

/**
 * tb_member 와 tb_memeber_slack_id 를 join 한 entity
 * <p>
 * email 과 slackId 를 매칭한다.
 */
@Setter
@Getter
@Entity
public class MemberWithSlackId {

  @Id
  @Column(name = "member_id")
  private long id;

  @Column
  private String memberEmail;

  @OneToOne
  @JoinColumn(name = "member_id")
  private MemeberSlackId slackId;

  @Column
  private boolean isDelete;

}
