package shopping.shop.member.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import shopping.shop.domain.Address;
import shopping.shop.member.domain.Member;
import shopping.shop.member.domain.MemberDto;
import shopping.shop.member.service.MemberService;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/add")
    public String openAddForm(Model model) {
        model.addAttribute("member", new MemberDto());
        return "members/addMemberForm";
    }

    @PostMapping("/add")
    public String save(@Valid @ModelAttribute("member") MemberDto dto,
                       BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "members/addMemberForm";
        }

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

        log.info("members={}", member);

        memberService.join(member);

        return "redirect:/login";
    }

    @GetMapping("/list")
    public String memberOpen(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }

    @GetMapping("/{id}")
    public String memberOpen(@PathVariable Long id, Model model) {

        Member member = memberService.findOne(id);
        log.info("memberOpen : zipcode ={}", member.getAddress().getZipcode());

        model.addAttribute("member", member);

        return "members/memberInfo";
    }

    @GetMapping("/{id}/edit")
    public String editMemberFormOpen(@PathVariable Long id, Model model) {

        Member member = memberService.findOne(id);
        model.addAttribute("member", member);
        return "members/editMemberForm";
    }

    @PostMapping("/{id}/edit")
    public String editMember(@PathVariable Long id, @ModelAttribute("member") Member member, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "members/editMemberForm";
        }

        log.info("memberDataController={}", member.getAddress().getZipcode());

        memberService.updateMember(id, member);

        return "redirect:/members/{id}";
    }

    /**
     * 테스트용 아이디 추가
     */
    @PostConstruct
    public void init() {
        memberService.join(new Member("test!", "test!"));
        memberService.join(new Member("asd", "asd", "asd", 10, "asd@asd",
                new Address("06112", "서울 강남구 논현로123길 4-1", "123", "(논현동)")));
    }
}