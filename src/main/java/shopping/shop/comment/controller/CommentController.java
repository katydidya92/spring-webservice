package shopping.shop.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import shopping.shop.comment.domain.Comment;
import shopping.shop.comment.service.CommentRepositoryImpl;
import shopping.shop.comment.service.CommentService;
import shopping.shop.login.session.SessionConst;
import shopping.shop.member.domain.Member;
import shopping.shop.post.domain.Post;
import shopping.shop.post.service.PostService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CommentController {

    private final PostService postService;

    private final CommentService service;
    private final CommentRepositoryImpl cmtService;

    @PostMapping("board/{postId}/comment_write")
    public String addComment(@ModelAttribute Comment comment,
                             @PathVariable Long postId,
                             @SessionAttribute(name = SessionConst.LOGIN_MEMBER,
                                     required = false) Member member) {

        service.commentWrite(comment, member, postId);
        return "redirect:/board/{postId}";
    }

    @GetMapping("board/{postId}/{cmtId}/comment_reply")
    public String addCmtReplyOpen(Model model,
                                  @PathVariable Long postId,
                                  @PathVariable Long cmtId,
                                  @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member) {

        Post post = postService.getById(postId);
        Comment comment = service.getById(cmtId);

        model.addAttribute("post", post);
        model.addAttribute("comment", comment);
        return "comments/replyCmt";
    }

    @PostMapping("board/{postId}/{cmtId}/comment_reply")
    public String addCmtReply(@ModelAttribute Comment comment,
                              @PathVariable Long postId,
                              @PathVariable Long cmtId,
                              @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member) {

        Comment cmt = Comment.builder()
                .cmtContent(comment.getCmtContent())
                .cmtReplyId(cmtId)
                .member(member)
                .post(comment.getPost())
                .build();

        service.cmtReplyWrite(cmt);
        return "redirect:/board/{postId}";
    }

    @GetMapping("board/{postId}/{cmtId}/comment_edit")
    public String editCommentOpen(@PathVariable Long cmtId,
                                  @PathVariable Long postId,
                                  Model model) {

        Comment comment = service.getById(cmtId);

        model.addAttribute("comment", comment);
        return "comments/editComment";
    }

    @PostMapping("board/{postId}/{cmtId}/comment_edit")
    public String editComment(@ModelAttribute Comment comment,
                              @PathVariable Long cmtId,
                              @PathVariable Long postId,
                              BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "boards/editPost";
        }

        cmtService.updateComment(comment, cmtId);
        return "redirect:/board/{postId}";
    }

    @GetMapping("board/{postId}/{cmtId}/comment_delete")
    public String deleteComment(@PathVariable Long cmtId, @PathVariable Long postId) {

        cmtService.deleteComment(cmtId);

        return "redirect:/board/{postId}";
    }

}
