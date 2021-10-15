package shopping.shop.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import shopping.shop.comment.domain.Comment;
import shopping.shop.comment.service.CommentService;
import shopping.shop.login.session.SessionConst;
import shopping.shop.member.domain.Member;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentApiController {

    private final CommentService service;

    @ResponseBody
    @PostMapping("/{postId}")
    public void addComment(
            @ModelAttribute Comment comment, @PathVariable Long postId,
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member) {

        log.info("postId:{}", postId);
        log.info("comment:{}", comment.getCmtReplyId());
        log.info("comment:{}", comment.getCmtContent());

        service.commentWrite(comment, member, postId);
    }

}