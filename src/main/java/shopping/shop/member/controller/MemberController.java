package shopping.shop.member.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import shopping.shop.common.Address;
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
    public String save(@Valid @ModelAttribute("member") MemberDto dto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "members/addMemberForm";
        }

        memberService.join(dto);
        return "redirect:/login";
    }

    @GetMapping("/list")
    public String memberOpen(Model model) {
        model.addAttribute("members", memberService.findMembers());
        return "members/memberList";
    }

    @GetMapping("/{id}")
    public String memberOpen(@PathVariable Long id, Model model) {
        model.addAttribute("member", memberService.findOne(id));
        return "members/memberInfo";
    }

    @GetMapping("/{id}/edit")
    public String editMemberFormOpen(@PathVariable Long id, Model model) {
        model.addAttribute("member", memberService.findOne(id));
        return "members/editMemberForm";
    }

    @PostMapping("/{id}/edit")
    public String editMember(@PathVariable Long id, @ModelAttribute("member") Member member, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "members/editMemberForm";
        }
        memberService.updateMember(id, member);
        return "redirect:/members/{id}";
    }

    /**
     * 테스트용 아이디 추가
     */
    @PostConstruct
    public void init() {
        memberService.join(
                new MemberDto(
                        new Member("test!", "test!", "asd", 10, "asd@asd",
                                new Address("06112", "서울 강남구 논현로123길 4-1", "123", "(논현동)"))));
        memberService.join(
                new MemberDto(
                        new Member("asd", "asd", "asd", 10, "asd@asd",
                                new Address("06112", "서울 강남구 논현로123길 4-1", "123", "(논현동)"))));
    }
}