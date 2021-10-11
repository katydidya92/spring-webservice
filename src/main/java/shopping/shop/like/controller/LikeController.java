package shopping.shop.like.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttribute;
import shopping.shop.member.domain.Member;
import shopping.shop.like.service.LikeService;
import shopping.shop.login.session.SessionConst;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @GetMapping("board/{postId}/like")
    public String addLike(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member,
                          @PathVariable Long postId) {

        if (member != null) {
            likeService.addLike(member, postId);
        }
        return "redirect:/boards/{postId}";
    }

    @GetMapping("board/{postId}/unlike")
    public String unlike(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member,
                          @PathVariable Long postId) {

        if (member != null) {
            likeService.unlike(member, postId);
        }
        return "redirect:/boards/{postId}";
    }

}
