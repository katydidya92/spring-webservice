package shopping.shop.member.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import shopping.shop.common.Address;
import shopping.shop.member.domain.Member;

import static org.assertj.core.api.Assertions.assertThat;

@Commit
@SpringBootTest
class MemberServiceTest {

    @Autowired MemberService memberService;

    @Test
    @Transactional
    void joinSuccess() {
        Member member = Member.builder()
                .userId("asd1")
                .userPw("asd1")
                .name("asd1")
                .age(10)
                .email("asd@asd")
                .address(Address.builder()
                        .zipcode("06112")
                        .roadAddr("서울 강남구 논현로123길 4-1")
                        .addrDetail("123")
                        .adEtc("(논현동)")
                        .build())
                .build();

        Long joinMemberId = memberService.join(member);
        Member findMember = memberService.findOne(joinMemberId);
        assertThat(findMember).isEqualTo(member);
    }

    /**
     * 필수 요소를 제외시키는 경우 오류 발생
     */
    @Test
    @Transactional
    void joinError() {
        Member member2 = Member.builder()
                .userId("asd2")
                .age(10)
                .email("asd@asd")
                .address(Address.builder()
                        .zipcode("06112")
                        .roadAddr("서울 강남구 논현로123길 4-1")
                        .addrDetail("123")
                        .adEtc("(논현동)")
                        .build())
                .build();

        Long joinMemberId2 = memberService.join(member2);
        Member findMember2 = memberService.findOne(joinMemberId2);
        assertThat(findMember2).isEqualTo(member2);
    }

    @Test
    public void updateMember() throws Exception {
        //given(주어지는 값)
        Long memberId = memberService.findMembers().get(0).getId();
        Member member = memberService.findOne(memberId);

        //when(조건)
        member.setAge(20);
        member.setEmail("fdke@naver.com");

        //then(테스트)
        memberService.updateMember(memberId, member);
    }

}