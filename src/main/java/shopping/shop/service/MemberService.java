package shopping.shop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.shop.dto.MemberDto;
import shopping.shop.repository.MemberRepository;
import shopping.shop.repository.MemberRepository2;
import shopping.shop.domain.Member;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberRepository2 memberRepository2;

    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository2.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository2.findById(member.getUserId());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    public List<Member> findMembers() {
        return memberRepository2.findAll();
    }

    public List<MemberDto> findMembers2() {
        return memberRepository2.findAll2();
    }

    public Member findOne(Long memberId) {
        return memberRepository2.findOne(memberId);
    }

    public String findUserId(String id) {
        return memberRepository.findUserId(id);
    }
}
