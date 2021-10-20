package shopping.shop.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.shop.common.Address;
import shopping.shop.member.domain.Member;
import shopping.shop.member.domain.MemberDto;
import shopping.shop.member.repository.MemberRepository;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long join(MemberDto dto) {

        Address address = Address.builder()
                .zipcode(dto.getZipcode())
                .roadAddr(dto.getRoadAddr())
                .addrDetail(dto.getAddrDetail())
                .adEtc(dto.getAdEtc()).build();

        Member member = Member.builder()
                .age(dto.getAge())
                .email(dto.getEmail())
                .name(dto.getName())
                .userId(dto.getUserId())
                .userPw(dto.getUserPw())
                .address(address)
                .build();

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
        Member member = memberRepository.findById(memberId).orElseThrow();
        return member;
    }

}