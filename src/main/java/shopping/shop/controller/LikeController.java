package shopping.shop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttribute;
import shopping.shop.domain.Member;
import shopping.shop.service.LikeService;
import shopping.shop.session.SessionConst;

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

        return "redirect:/board/{postId}";
    }

}
