package com.devtaco.slacknotifyservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.devtaco.slacknotifyservice.entity.MemberWithSlackId;

public interface MemeberWithSlackIdRepo extends JpaRepository<MemberWithSlackId, String>{
  @Query(value = "SELECT * FROM tb_member tm JOIN tb_member_slack_id tmsi ON tm.id = tmsi.member_id WHERE tm.member_email = :memberEmail", nativeQuery = true)
  Optional<MemberWithSlackId> findOneByMemberEmail(@Param("memberEmail") String memberEmail);
}
