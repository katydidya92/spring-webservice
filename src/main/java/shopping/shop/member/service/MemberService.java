package shopping.shop.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.shop.common.Address;
import shopping.shop.member.domain.Member;
import shopping.shop.member.domain.MemberDto;
import shopping.shop.member.repository.MemberRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long join(MemberDto dto) {

//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        dto.setPassword(encoder.encode(dto.getPassword()));

        return memberRepository.save(dto.toEntity()).getId();
    }

    @Transactional
    public void updateMember(Long memberId, Member member) {
        Member memberData = memberRepository.getById(memberId);
        log.info("memberData={}", memberData.getAddress().getZipcode());
        memberData.setAge(member.getAge());
        memberData.setName(member.getName());
        memberData.setEmail(member.getEmail());
        memberData.setAddress(Address.builder()
                .zipcode(member.getAddress().getZipcode())
                .roadAddr(member.getAddress().getRoadAddr())
                .addrDetail(member.getAddress().getAddrDetail())
                .adEtc(member.getAddress().getAdEtc()).build()
        );
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow();
    }

    @Override
    public Member loadUserByUsername(String userId) throws UsernameNotFoundException {
        return memberRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException((userId)));
    }
}