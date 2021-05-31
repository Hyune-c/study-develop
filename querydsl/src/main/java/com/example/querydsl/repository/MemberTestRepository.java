package com.example.querydsl.repository;

import static com.example.querydsl.entity.QMember.member;
import static com.example.querydsl.entity.QTeam.team;
import static org.springframework.util.StringUtils.hasText;

import com.example.querydsl.dto.MemberSearchCondition;
import com.example.querydsl.entity.Member;
import com.example.querydsl.repository.support.Querydsl4RepositorySupport;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@Repository
public class MemberTestRepository extends Querydsl4RepositorySupport {

  public MemberTestRepository() {
    super(Member.class);
  }

  public List<Member> basicSelect() {
    return select(member)
        .from(member)
        .fetch();
  }

  public List<Member> basicSelectFrom() {
    return selectFrom(member)
        .fetch();
  }

  public Page<Member> searchPageByApplyPage(MemberSearchCondition condition, Pageable pageable) {
    JPAQuery<Member> query = selectFrom(member)
        .leftJoin(member.team, team)
        .where(usernameEq(condition.getUsername()),
            teamNameEq(condition.getTeamName()),
            ageGoe(condition.getAgeGoe()),
            ageLoe(condition.getAgeLoe()));
    List<Member> content = getQuerydsl()
        .applyPagination(pageable, query)
        .fetch();

    return PageableExecutionUtils.getPage(content, pageable, query::fetchCount);
  }

  public Page<Member> applyPagination(MemberSearchCondition condition, Pageable pageable) {
    return applyPagination(pageable, contentQuery -> contentQuery.selectFrom(member));
  }

  private BooleanExpression usernameEq(String username) {
    return (hasText(username)) ? member.username.eq(username) : null;
  }

  private BooleanExpression teamNameEq(String teamName) {
    return (hasText(teamName)) ? team.name.eq(teamName) : null;
  }

  private BooleanExpression ageGoe(Long ageGoe) {
    return (ageGoe == null) ? null : member.age.goe(ageGoe);
  }

  private BooleanExpression ageLoe(Long ageLoe) {
    return (ageLoe == null) ? null : member.age.loe(ageLoe);
  }
}
