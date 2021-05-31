package jpabook.jpashop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {

	private final MemberRepository memberRepository;

	@Transactional
	public Long join(Member member) {
		validateDuplicateMember(member);
		memberRepository.save(member);

		return member.getId();
	}

	private void validateDuplicateMember(Member member) {
		if (!memberRepository.findByName(member.getName()).isEmpty()) {
			throw new IllegalStateException("이미 존재하는 회원입니다.");
		}
	}

	public List<Member> findMembers() {
		return memberRepository.findAll();
	}

	public Member findOne(Long memberId) {
		return memberRepository.findById(memberId).get();
	}

	@Transactional
	public Long update(Long memberId, String name) {
		Member member = memberRepository.findById(memberId).get();
		member.setName(name);

		return member.getId();
	}
}
