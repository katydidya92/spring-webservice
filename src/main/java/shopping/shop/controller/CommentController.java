package shopping.shop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import shopping.shop.domain.Comment;
import shopping.shop.domain.Member;
import shopping.shop.service.CommentRepositoryImpl;
import shopping.shop.service.CommentService;
import shopping.shop.session.SessionConst;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService service;
    private final CommentRepositoryImpl cmtService;

    @PostMapping("board/{postId}/comment_write")
    public String addComment(@ModelAttribute Comment comment, @PathVariable Long postId, @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member) {
        service.commentWrite(comment, member, postId);
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

        cmtService.updateComment(comment, postId);
        return "redirect:/board/{postId}";
    }


}
