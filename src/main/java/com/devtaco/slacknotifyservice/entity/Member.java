package com.devtaco.slacknotifyservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * tb_member 와 1:1 대응 되는 entity
 * <p>
 * member 의 email 과 name 를 참조한다.
 */
@Getter
@Setter
@Entity(name = "tb_member")
@Table(name = "tb_member")
public class Member {

  @Id
  @GeneratedValue
  private long id;

  @Column
  private String memberEmail;

  @Column
  private String description;

  @Column
  private String memberName;

  @Column
  private long regDt;

  @Column
  private boolean isDelete;
}
