package shopping.shop.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import shopping.shop.domain.Address;
import shopping.shop.domain.Member;
import shopping.shop.dto.MemberDto;
import shopping.shop.service.MemberService;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Getter
@Slf4j
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/add")
    public String openAddForm(Model model) {
        model.addAttribute("member", new MemberDto());
        return "members/addMemberForm";
    }

    @PostMapping("/add")
    public String save(@Validated @ModelAttribute("member") MemberDto dto,
                       BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "members/addMemberForm";
        }

        Member member = new Member();

        Address address = new Address(dto.getZipcode(), dto.getRoadAddr(), dto.getAddrDetail(), dto.getAdEtc());

        member.setAge(dto.getAge());
        member.setEmail(dto.getEmail());
        member.setName(dto.getName());
        member.setUserId(dto.getUserId());
        member.setUserPw(dto.getUserPw());
        member.setAddress(address);
        log.info("members={}", member);

        memberService.join(member);

        return "redirect:/";
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
        log.info("memberOpen : zipcode ={}",member.getAddress().getZipcode());

        model.addAttribute("member", member);

        return "members/memberInfo";
    }

    /**
     * 테스트용 아이디 추가
     */
    @PostConstruct
    public void init() {
        memberService.join(new Member("test!", "test!"));
        memberService.join(new Member("asd", "asd"));
    }
}
