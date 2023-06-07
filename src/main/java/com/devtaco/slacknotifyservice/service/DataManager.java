package com.devtaco.slacknotifyservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.devtaco.slacknotifyservice.entity.Member;
import com.devtaco.slacknotifyservice.entity.MemberWithSlackId;
import com.devtaco.slacknotifyservice.model.AppMessage2Mongo;
import com.devtaco.slacknotifyservice.repository.MemberRepo;
import com.devtaco.slacknotifyservice.repository.MemeberWithSlackIdRepo;
import com.devtaco.slacknotifyservice.repository.NotifyInfoRepository;

/**
 * Postgres, DocumentDB 관련 interface(Repository) 를 모두 들고 있는 facade
 */
@Component
public class DataManager {

  private final NotifyInfoRepository notifiyInfoRepo;
  private final MemberRepo memberRepo;
  private final MemeberWithSlackIdRepo slackIdRepo;

  public DataManager(NotifyInfoRepository notifiyInfoRepo, MemberRepo memberRepo, MemeberWithSlackIdRepo slackIdRepo) {
    this.notifiyInfoRepo = notifiyInfoRepo;
    this.memberRepo = memberRepo;
    this.slackIdRepo = slackIdRepo;
  }

  // ------------- POSTGRES -------------------

  public List<Member> findAll() {
    return memberRepo.findAll();
  }

  public Member findMemeberByEmail(String memberEmail) {
    return memberRepo.findOneByMemberEmail(memberEmail);
  }

  public MemberWithSlackId findSlackIdByEmail(String memberEmail) {
    Optional<MemberWithSlackId> memberWithSlackId = slackIdRepo.findOneByMemberEmail(memberEmail);
    if (!memberWithSlackId.isPresent()) {
      throw new IllegalArgumentException();
    }
    return memberWithSlackId.get();
  }

  // ------------- MONGO -------------------

  public List<AppMessage2Mongo> findAllLogs() {
    return this.notifiyInfoRepo.findAll();
  }

  public void saveLogs(AppMessage2Mongo logs) {
    this.notifiyInfoRepo.saveAppMessage(logs);
  }

}
