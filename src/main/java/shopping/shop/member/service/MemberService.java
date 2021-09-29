package shopping.shop.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.shop.domain.Address;
import shopping.shop.member.domain.Member;
import shopping.shop.member.repository.MemberRepository;
import shopping.shop.member.repository.MemberRepository2;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberRepository2 memberRepository2;

    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    @Transactional
    public void updateMember(Long memberId, Member member) {
        Member memberData = memberRepository.getById(memberId);
        log.info("memberData1={}", memberData.getAddress().getZipcode());
        memberData.setAge(member.getAge());
        memberData.setName(member.getName());
        memberData.setEmail(member.getEmail());
        memberData.setAddress(Address.builder()
                .zipcode(member.getAddress().getZipcode())
                .roadAddr(member.getAddress().getRoadAddr())
                .addrDetail(member.getAddress().getAddrDetail())
                .adEtc(member.getAddress().getAdEtc()).build()
        );
        log.info("memberData2={}", memberData.getAddress().getZipcode());
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findUserId(member.getUserId());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository2.findOne(memberId);
    }

}