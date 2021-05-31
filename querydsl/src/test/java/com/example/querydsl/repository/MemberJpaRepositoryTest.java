package com.example.querydsl.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.querydsl.dto.MemberSearchCondition;
import com.example.querydsl.dto.MemberTeamDto;
import com.example.querydsl.entity.Member;
import com.example.querydsl.entity.Team;
import java.util.List;
import javax.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@ActiveProfiles("test")
@SpringBootTest
class MemberJpaRepositoryTest {

  @Autowired
  private EntityManager em;

  @Autowired
  private MemberJpaRepository memberJpaRepository;

  @Test
  public void basicTest() {
    // given
    Member member = new Member("member1", 10);
    memberJpaRepository.save(member);

    // when
    Member findMember = memberJpaRepository.findById(member.getId()).get();
    List<Member> result1 = memberJpaRepository.findAll();
    List<Member> result2 = memberJpaRepository.findByUsername("member1");

    // then
    assertThat(findMember).isEqualTo(member);
    assertThat(result1).containsExactly(member);
    assertThat(result2).containsExactly(member);
  }

  @Test
  public void basicQuerydsl() {
    // given
    Member member = new Member("member1", 10);
    memberJpaRepository.save(member);

    // when
    Member findMember = memberJpaRepository.findById(member.getId()).get();
    List<Member> result1 = memberJpaRepository.findAll_Querydsl();
    List<Member> result2 = memberJpaRepository.findByUsername_Querydsl("member1");

    // then
    assertThat(findMember).isEqualTo(member);
    assertThat(result1).containsExactly(member);
    assertThat(result2).containsExactly(member);
  }

  @Test
  public void searchTest() {
    // given
    Team teamA = new Team("teamA");
    Team teamB = new Team("teamB");
    em.persist(teamA);
    em.persist(teamB);

    Member member1 = new Member("member1", 10, teamA);
    Member member2 = new Member("member2", 20, teamA);
    Member member3 = new Member("member3", 30, teamB);
    Member member4 = new Member("member4", 40, teamB);
    em.persist(member1);
    em.persist(member2);
    em.persist(member3);
    em.persist(member4);

    // when
    MemberSearchCondition condition = new MemberSearchCondition();
    condition.setAgeGoe(35L);
    condition.setAgeLoe(40L);
    condition.setTeamName("teamB");

    List<MemberTeamDto> resultByBuilder = memberJpaRepository.searchByBuilder(condition);
    List<MemberTeamDto> result = memberJpaRepository.search(condition);

    // then
    assertThat(resultByBuilder).extracting("username").containsExactly("member4");
    assertThat(result).extracting("username").containsExactly("member4");
  }
}
