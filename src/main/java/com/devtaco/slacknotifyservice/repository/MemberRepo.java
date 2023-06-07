package com.devtaco.slacknotifyservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.devtaco.slacknotifyservice.entity.Member;

public interface MemberRepo extends JpaRepository<Member, String>{
  @Query(value = "SELECT * FROM tb_member tm WHERE tm.member_email = :memberEmail", nativeQuery = true)
  Member findOneByMemberEmail(@Param("memberEmail") String memberEmail);
}
