package com.example.springdatajpa.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.springdatajpa.entity.Member;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@ActiveProfiles("test")
@SpringBootTest
class MemberRepositoryTest {

  @Autowired
  private MemberRepository memberRepository;

  @PersistenceContext
  private EntityManager em;

  @Test
  public void testMember() {
    Member member = new Member("memberA");
    Member savedMember = memberRepository.save(member);
    Member findMember =
        memberRepository.findById(savedMember.getId()).get();

    assertThat(findMember.getId()).isEqualTo(member.getId());
    assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
    assertThat(findMember).isEqualTo(member);
  }

  @Test
  public void basicCRUD() {
    Member member1 = new Member("member1");
    Member member2 = new Member("member2");
    memberRepository.save(member1);
    memberRepository.save(member2);

    //단건 조회 검증
    Member findMember1 = memberRepository.findById(member1.getId()).get();
    Member findMember2 = memberRepository.findById(member2.getId()).get();
    assertThat(findMember1).isEqualTo(member1);
    assertThat(findMember2).isEqualTo(member2);

    //리스트 조회 검증
    List<Member> all = memberRepository.findAll();
    assertThat(all.size()).isEqualTo(2);

    //카운트 검증
    long count = memberRepository.count();
    assertThat(count).isEqualTo(2);

    //삭제 검증
    memberRepository.delete(member1);
    memberRepository.delete(member2);
    long deletedCount = memberRepository.count();
    assertThat(deletedCount).isEqualTo(0);
  }

  @Test
  public void paging() {
    //given
    memberRepository.save(new Member("member1", 10));
    memberRepository.save(new Member("member2", 10));
    memberRepository.save(new Member("member3", 10));
    memberRepository.save(new Member("member4", 10));
    memberRepository.save(new Member("member5", 10));

    int age = 10;
    PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));

    //when
    Page<Member> members = memberRepository.findByAge(age, pageRequest);

    //then
    assertThat(members.getContent().size()).isEqualTo(3);
    assertThat(members.getTotalElements()).isEqualTo(5);
    assertThat(members.getNumber()).isEqualTo(0);
    assertThat(members.getTotalPages()).isEqualTo(2);
    assertThat(members.isFirst()).isEqualTo(true);
    assertThat(members.hasNext()).isEqualTo(true);
  }

  @Test
  public void bulkUpdate() {
    //given
    memberRepository.save(new Member("member1", 10));
    memberRepository.save(new Member("member2", 19));
    memberRepository.save(new Member("member3", 20));
    memberRepository.save(new Member("member4", 21));
    memberRepository.save(new Member("member5", 40));

    //when
    int resultCount = memberRepository.bulkAgePlus(20);

    //then
    assertThat(resultCount).isEqualTo(3);
  }

  @Test
  public void queryHint() {
    //given
    memberRepository.save(new Member("member1", 10));
    em.flush();
    em.clear();

    //when
    Member member = memberRepository.findReadOnlyByUsername("member1");
    member.setUsername("member2");
    em.flush();
  }
}
