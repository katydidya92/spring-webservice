package shopping.shop.login.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import shopping.shop.login.repository.LoginRepository;
import shopping.shop.member.domain.Member;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

    private final LoginRepository loginRepository;

    public Member login(String loginId, String loginPw) {
        Member member = loginRepository.findByLoginId(loginId)
                .filter(m -> m.getUserPw().equals(loginPw))
                .orElse(null);
        return member;
    }
}
