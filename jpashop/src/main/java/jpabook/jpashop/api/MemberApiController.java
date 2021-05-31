package jpabook.jpashop.api;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jpabook.jpashop.api.request.CreateMemberRequest;
import jpabook.jpashop.api.request.UpdateMemberRequest;
import jpabook.jpashop.api.response.CreateMemberResponse;
import jpabook.jpashop.api.response.MemberDto;
import jpabook.jpashop.api.response.Result;
import jpabook.jpashop.api.response.UpdateMemberResponse;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class MemberApiController {

	private final MemberService memberService;

	@PostMapping("/api/v1/members")
	public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
		return new CreateMemberResponse(memberService.join(member));
	}

	@PostMapping("/api/v2/members")
	public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {
		Member member = new Member();
		member.setName(request.getName());

		return new CreateMemberResponse(memberService.join(member));
	}

	@PutMapping("/api/v2/members/{memberId}")
	public UpdateMemberResponse updateMemberV2(@PathVariable Long memberId,
		@RequestBody @Valid UpdateMemberRequest request) {
		Long updatedId = memberService.update(memberId, request.getName());
		Member member = memberService.findOne(updatedId);
		return new UpdateMemberResponse(member.getId(), member.getName());
	}

	@GetMapping("/api/v1/members")
	public List<Member> memberV1() {
		return memberService.findMembers();
	}

	@GetMapping("/api/v2/members")
	public Result memberV2() {
		return new Result(memberService.findMembers().stream()
			.map(member -> new MemberDto(member.getName()))
			.collect(Collectors.toList()));
	}
}
