package com.example.springdatajpa.repository;

import com.example.springdatajpa.entity.Member;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepositoryCustom {

  List<Member> findMemberCustom();
}
