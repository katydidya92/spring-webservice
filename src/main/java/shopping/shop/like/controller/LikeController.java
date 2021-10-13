package shopping.shop.like.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import shopping.shop.like.service.LikeService;
import shopping.shop.login.session.SessionConst;
import shopping.shop.member.domain.Member;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/likes")
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/{postId}")
    public void addLike(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member,
                        @PathVariable Long postId) {
        if (member != null) {
            likeService.addLike(member, postId);
        }
    }

    @DeleteMapping("/{postId}")
    public void unlike(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member,
                       @PathVariable Long postId) {
        if (member != null) {
            likeService.unlike(member, postId);
        }
    }

}
