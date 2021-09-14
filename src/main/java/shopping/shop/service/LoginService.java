package shopping.shop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import shopping.shop.domain.Member;
import shopping.shop.repository.MemberRepository2;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository2 memberRepository2;

    public Member login(String loginId, String loginPw) {
        return memberRepository2.findByLoginId(loginId)
                .filter(m -> m.getUserPw().equals(loginPw))
                .orElse(null);
    }
}
