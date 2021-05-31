package com.example.querydsl.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.querydsl.dto.MemberSearchCondition;
import com.example.querydsl.dto.MemberTeamDto;
import com.example.querydsl.entity.Member;
import com.example.querydsl.entity.Team;
import java.util.List;
import javax.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@ActiveProfiles("test")
@SpringBootTest
class MemberRepositoryTest {

  @Autowired
  private EntityManager em;

  @Autowired
  private MemberRepository memberRepository;

  @BeforeEach
  public void beforeEach() {
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

    log.info("### beforeEach end");
  }

  @Test
  public void basicTest() {
    // given
    Member member = new Member("member5", 50);
    memberRepository.save(member);

    // when
    Member findMember = memberRepository.findById(member.getId()).get();
    List<Member> result1 = memberRepository.findAll();
    List<Member> result2 = memberRepository.findByUsername("member5");

    // then
    assertThat(findMember).isEqualTo(member);
    assertThat(result1.size()).isEqualTo(5);
    assertThat(result2).containsExactly(member);
  }

  @Test
  public void searchTest() {
    // given

    // when
    MemberSearchCondition condition = new MemberSearchCondition();
    condition.setAgeGoe(35L);
    condition.setAgeLoe(40L);
    condition.setTeamName("teamB");

    List<MemberTeamDto> result = memberRepository.search(condition);

    // then
    assertThat(result).extracting("username").containsExactly("member4");
  }

  @Test
  public void searchPageSimple() {
    // given

    // when
    MemberSearchCondition condition = new MemberSearchCondition();
    PageRequest pageSimple = PageRequest.of(0, 3);

    Page<MemberTeamDto> result = memberRepository.searchPageSimple(condition, pageSimple);

    // then
    assertThat(result.getSize()).isEqualTo(3);
    assertThat(result.getContent()).extracting("username").containsExactly("member1", "member2", "member3");
  }
}
