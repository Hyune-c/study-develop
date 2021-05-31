package com.example.querydsl;

import static com.example.querydsl.entity.QMember.member;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.querydsl.dto.MemberDto;
import com.example.querydsl.dto.QMemberDto;
import com.example.querydsl.dto.UserDto;
import com.example.querydsl.entity.Member;
import com.example.querydsl.entity.QMember;
import com.example.querydsl.entity.Team;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@ActiveProfiles("test")
@SpringBootTest
class QuerydslIntermediateTest {

  @PersistenceContext
  private EntityManager em;

  private JPAQueryFactory queryFactory;


  @PostConstruct
  public void postConstruct() {
    queryFactory = new JPAQueryFactory(em);
  }

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
  public void findDto() {
    // given
    QMember memberSub = new QMember("memberSub");

    // when
    List<MemberDto> resultBySetter = queryFactory
        .select(
            Projections.bean(MemberDto.class,
                member.username,
                member.age))
        .from(member)
        .fetch();

    List<MemberDto> resultByField = queryFactory
        .select(Projections.fields(MemberDto.class,
            member.username,
            member.age))
        .from(member)
        .fetch();

    List<MemberDto> resultByConstructor = queryFactory
        .select(Projections.constructor(MemberDto.class,
            member.username,
            member.age))
        .from(member)
        .fetch();

    List<UserDto> resultByExpressionUtils = queryFactory
        .select(Projections.fields(UserDto.class,
            member.username.as("name"),
            ExpressionUtils.as(JPAExpressions
                .select(memberSub.age.max())
                .from(memberSub), "age")))
        .from(member)
        .fetch();

    // then
    resultBySetter.forEach(dto -> log.info("### bySetter memberDto: {}", dto));
    resultByField.forEach(dto -> log.info("### byField memberDto: {}", dto));
    resultByConstructor.forEach(dto -> log.info("### byConstructor memberDto: {}", dto));
    resultByExpressionUtils.forEach(dto -> log.info("### byExpressionUtils userDto: {}", dto));
  }

  @Test
  public void findDtoByQueryProjection() {
    // given

    // when
    List<MemberDto> result = queryFactory
        .select(new QMemberDto(member.username, member.age))
        .from(member)
        .fetch();

    // then
    result.forEach(dto -> log.info("### result: {}", dto));
  }

  @Test
  public void dynamicQuery_BooleanBuilder() {
    // given
    String usernameParam = "member1";
    Integer ageParam = null;

    // when
    List<Member> result = searchMember1(usernameParam, ageParam);

    // then
    assertThat(result.size()).isEqualTo(1);
  }

  private List<Member> searchMember1(String usernameCond, Integer ageCond) {
    BooleanBuilder builder = new BooleanBuilder();

    if (usernameCond != null) {
      builder.and(member.username.eq(usernameCond));
    }

    if (ageCond != null) {
      builder.and(member.age.eq(ageCond));
    }

    return queryFactory
        .selectFrom(member)
        .where(builder)
        .fetch();
  }

  @Test
  public void dynamicQuery_WhereParam() {
    // given
    String usernameParam = "member1";
    Integer ageParam = null;

    // when
    List<Member> result = searchMember2(usernameParam, ageParam);

    // then
    assertThat(result.size()).isEqualTo(1);
  }

  private List<Member> searchMember2(String usernameCond, Integer ageCond) {
    return queryFactory
        .selectFrom(member)
        .where(usernameEq(usernameCond), ageEq(ageCond))
        .fetch();
  }

  private BooleanExpression ageEq(Integer ageCond) {
    return (ageCond == null) ? null : member.age.eq(ageCond);
  }

  private BooleanExpression usernameEq(String usernameCond) {
    return (usernameCond == null) ? null : member.username.eq(usernameCond);
  }

  private BooleanExpression allEq(String usernameCond, Integer ageCond) {
    return usernameEq(usernameCond).and(ageEq(ageCond));
  }

  @Test
  public void bulkUpdate() {
    // given
    queryFactory.update(member)
        .set(member.username, "비회원")
        .where(member.age.lt(28))
        .execute();

    assertThat(queryFactory
        .selectFrom(member)
        .fetch().get(0).getUsername()).isEqualTo("member1");

    // when

    em.flush();
    em.clear();

    // then
    assertThat(queryFactory
        .selectFrom(member)
        .fetch().get(0).getUsername()).isEqualTo("비회원");
  }

  @Test
  public void bulkAdd() {
    // given

    // when
    queryFactory.update(member)
        .set(member.age, member.age.add(1))
        .execute();

    // then
  }
}
