package com.example.springdatajpa.repository;

import com.example.springdatajpa.entity.Member;
import java.util.List;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class MemberRepositoryImpl implements MemberRepositoryCustom {

  private final EntityManager em;

  @Override
  public List<Member> findMemberCustom() {
    return em.createQuery("select m from Member m")
        .getResultList();
  }
}
