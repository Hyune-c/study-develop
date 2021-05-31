package com.example.querydsl;

import static com.example.querydsl.entity.QMember.member;
import static com.example.querydsl.entity.QTeam.team;
import static com.querydsl.jpa.JPAExpressions.select;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.querydsl.entity.Member;
import com.example.querydsl.entity.QMember;
import com.example.querydsl.entity.Team;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
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
class QuerydslBasicTest {

  @PersistenceContext
  private EntityManager em;

  private JPAQueryFactory queryFactory;
  private QMember memberSub;

  @PostConstruct
  public void postConstruct() {
    memberSub = new QMember("memberSub");
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
  public void startQuerydsl() {
    // given

    // when
    Member findMember = queryFactory.select(member)
        .from(member)
        .where(member.username.eq("member1"))
        .fetchOne();

    // then
    assertThat(findMember.getUsername()).isEqualTo("member1");
  }

  @Test
  public void search() {
    // given

    // when
    Member findMember = queryFactory.selectFrom(member)
        .where(member.username.eq("member1")
            .and(member.age.eq(10)))
        .fetchOne();

    // then
    assertThat(findMember.getUsername()).isEqualTo("member1");
  }

  @Test
  public void searchAndParam() {
    // given

    // when
    Member findMember = queryFactory.selectFrom(member)
        .where(member.username.eq("member1"),
            (member.age.eq(10)))
        .fetchOne();

    // then
    assertThat(findMember.getUsername()).isEqualTo("member1");
  }

  @Test
  public void resultFetch() {
    // given

    // when
    List<Member> fetch = queryFactory.selectFrom(member).fetch();
//    Member fetchOne = queryFactory.selectFrom(member).fetchOne();
    Member fetchFirst = queryFactory.selectFrom(member).fetchFirst();
    QueryResults<Member> results = queryFactory.selectFrom(member).fetchResults();
    long count = queryFactory.selectFrom(member).fetchCount();

    // then
    List<Member> content = results.getResults();
  }

  @Test
  public void sort() {
    // given
    em.persist(new Member(null, 100));
    em.persist(new Member("member5", 100));
    em.persist(new Member("member6", 100));

    // when
    List<Member> result = queryFactory.selectFrom(member)
        .where(member.age.eq(100))
        .orderBy(member.age.desc(), member.username.asc().nullsLast())
        .fetch();

    // then
    Member member5 = result.get(0);
    Member member6 = result.get(1);
    Member memberNull = result.get(2);
    assertThat(member5.getUsername()).isEqualTo("member5");
    assertThat(member6.getUsername()).isEqualTo("member6");
    assertThat(memberNull.getUsername()).isNull();
  }

  @Test
  public void paging1() {
    // given

    // when
    List<Member> result = queryFactory.selectFrom(member)
        .orderBy(member.username.desc())
        .offset(1)
        .limit(2)
        .fetch();

    // then
    assertThat(result.size()).isEqualTo(2);
  }

  @Test
  public void paging2() {
    // given

    // when
    QueryResults<Member> queryResults = queryFactory.selectFrom(member)
        .orderBy(member.username.desc())
        .offset(1)
        .limit(2)
        .fetchResults();

    // then
    assertThat(queryResults.getTotal()).isEqualTo(4);
    assertThat(queryResults.getLimit()).isEqualTo(2);
    assertThat(queryResults.getOffset()).isEqualTo(1);
    assertThat(queryResults.getResults().size()).isEqualTo(2);
  }

  @Test
  public void aggregation() {
    // given

    // when
    List<Tuple> result = queryFactory
        .select(
            member.count(),
            member.age.sum(),
            member.age.avg(),
            member.age.max(),
            member.age.min())
        .from(member)
        .fetch();

    // then
    Tuple tuple = result.get(0);
    assertThat(tuple.get(member.count())).isEqualTo(4);
    assertThat(tuple.get(member.age.sum())).isEqualTo(100);
    assertThat(tuple.get(member.age.avg())).isEqualTo(25);
    assertThat(tuple.get(member.age.max())).isEqualTo(40);
    assertThat(tuple.get(member.age.min())).isEqualTo(10);
  }

  @Test
  public void group() {
    // given

    // when
    List<Tuple> result = queryFactory
        .select(team.name, member.age.avg())
        .from(member)
        .join(member.team, team)
        .groupBy(team.name)
        .fetch();

    // then
    Tuple teamA = result.get(0);
    Tuple teamB = result.get(1);
    assertThat(teamA.get(team.name)).isEqualTo("teamA");
    assertThat(teamA.get(member.age.avg())).isEqualTo(15);
    assertThat(teamB.get(team.name)).isEqualTo("teamB");
    assertThat(teamB.get(member.age.avg())).isEqualTo(35);
  }

  @Test
  public void join() {
    // given

    // when
    List<Member> result = queryFactory
        .selectFrom(member)
        .join(member.team, team)
        .where(team.name.eq("teamA"))
        .fetch();

    // then
    assertThat(result).extracting("username").containsExactly("member1", "member2");
  }

  @Test
  public void theta_join() {
    // given
    em.persist(new Member("teamA"));
    em.persist(new Member("teamB"));

    // where
    List<Member> result = queryFactory
        .select(member)
        .from(member, team)
        .where(member.username.eq(team.name))
        .fetch();

    // then
    assertThat(result).extracting("username").containsExactly("teamA", "teamB");
  }

  @Test
  public void join_on_filtering() {
    // given

    // when
    List<Tuple> result = queryFactory
        .select(member, team)
        .from(member)
        .leftJoin(member.team, team).on(team.name.eq("teamA"))
        .fetch();

    // then
    result.forEach(tuple -> log.info("### tuple: {}", tuple));
  }

  @Test
  public void join_on_no_relation() {
    // given
    em.persist(new Member("teamA"));
    em.persist(new Member("teamB"));

    // when
    List<Tuple> result = queryFactory
        .select(member, team)
        .from(member)
        .leftJoin(team).on(member.username.eq(team.name))
        .fetch();

    // then
    result.forEach(tuple -> log.info("### tuple: {}", tuple));
  }

  @PersistenceUnit
  EntityManagerFactory emf;

  @Test
  public void fetchJoinNo() {
    // given
    em.flush();
    em.clear();

    // when
    Member findMember = queryFactory
        .selectFrom(member)
        .where(member.username.eq("member1"))
        .fetchOne();

    // then
    assertThat(emf.getPersistenceUnitUtil().isLoaded(findMember.getTeam())).isFalse();
  }

  @Test
  public void fetchJoinUse() {
    // given
    em.flush();
    em.clear();

    // when
    Member findMember = queryFactory
        .selectFrom(member)
        .join(member.team, team).fetchJoin()
        .where(member.username.eq("member1"))
        .fetchOne();

    // then
    assertThat(emf.getPersistenceUnitUtil().isLoaded(findMember.getTeam())).isTrue();
  }

  @Test
  public void subQuery() {
    // given

    // when
    List<Member> result = queryFactory
        .selectFrom(member)
        .where(member.age.eq(
            select(memberSub.age.max())
                .from(memberSub)))
        .fetch();

    // then
    assertThat(result).extracting("age").containsExactly(40);
  }

  @Test
  public void subQueryGoe() {
    // given

    // when
    List<Member> result = queryFactory
        .selectFrom(member)
        .where(member.age.goe(
            select(memberSub.age.avg())
                .from(memberSub)))
        .fetch();

    // then
    assertThat(result).extracting("age").containsExactly(30, 40);
  }

  @Test
  public void subQueryIn() {
    // given

    // when
    List<Member> result = queryFactory
        .selectFrom(member)
        .where(member.age.in(
            select(memberSub.age)
                .from(memberSub)
                .where(memberSub.age.gt(10))))
        .fetch();

    // then
    assertThat(result).extracting("age").containsExactly(20, 30, 40);
  }

  @Test
  public void subSubQuery() {
    // given

    // when
    List<Tuple> result = queryFactory
        .select(member.username,
            select(memberSub.age.avg())
                .from(memberSub))
        .from(member)
        .fetch();

    // then
    result.forEach(tuple -> {
      log.info("### username: {}", tuple.get(member.username));
      log.info("### age: {}", tuple.get(select(memberSub.age.avg()).from(memberSub)));
    });
  }

  @Test
  public void basicCase() {
    // given

    // when
    List<String> result = queryFactory
        .select(member.age
            .when(10).then("열살")
            .when(20).then("스무살")
            .otherwise("기타"))
        .from(member)
        .fetch();

    // then
    result.forEach(string -> log.info("### string: {}", string));
  }

  @Test
  public void complexCase() {
    // given

    // when
    List<String> result = queryFactory
        .select(new CaseBuilder()
            .when(member.age.between(0, 20)).then("0~20살")
            .when(member.age.between(21, 30)).then("21~30살")
            .otherwise("기타"))
        .from(member)
        .fetch();

    // then
    result.forEach(string -> log.info("### string: {}", string));
  }

  @Test
  public void constant() {
    // given

    // when
    List<Tuple> result = queryFactory
        .select(member.username, Expressions.constant("A"))
        .from(member)
        .fetch();

    // then
    result.forEach(tuple -> log.info("### tuple: {}", tuple));
  }

  @Test
  public void concat() {
    // given

    // when
    List<String> result = queryFactory
        .select(member.username.concat("_").concat(member.age.stringValue()))
        .from(member)
        .where(member.username.eq("member1"))
        .fetch();

    // then
    result.forEach(tuple -> log.info("### tuple: {}", tuple));
  }
}
