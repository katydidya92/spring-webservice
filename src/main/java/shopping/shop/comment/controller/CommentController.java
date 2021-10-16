package shopping.shop.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import shopping.shop.comment.domain.CmtResponseDto;
import shopping.shop.comment.domain.Comment;
import shopping.shop.comment.service.CommentRepositoryImpl;
import shopping.shop.comment.service.CommentService;
import shopping.shop.login.session.SessionConst;
import shopping.shop.member.domain.Member;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentService service;
    private final CommentRepositoryImpl cmtService;

    @GetMapping("/{cmtId}")
    public String cmtOpen(@PathVariable Long cmtId, Model model, RedirectAttributes attributes,
                          @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member) {

        CmtResponseDto comment = service.findById(cmtId);

        if (member.getUserId() == comment.getUserId()) {
            model.addAttribute("comment", comment);
            return "comments/editComment";
        }
        attributes.addAttribute("postId", comment.getPostId());
        return "redirect:/boards/{postId}";
    }

    @PostMapping("/{cmtId}")
    public String editComment(@ModelAttribute Comment comment, @PathVariable Long cmtId, BindingResult bindingResult,
                              RedirectAttributes attributes, @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member) {

        if (bindingResult.hasErrors() || member.getUserId() == comment.getUserId()) {
            log.info("errors={}", bindingResult);
            return "boards/editPost";
        }

        cmtService.updateComment(comment, cmtId);
        attributes.addAttribute("postId", comment.getPostId());
        return "redirect:/boards/{postId}";
    }

    @GetMapping("/reply/{cmtId}")
    public String addCmtReplyOpen(Model model, @PathVariable Long cmtId, RedirectAttributes attributes,
                                  @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member) {

        Comment comment = service.getById(cmtId);
        if (member.getUserId() == comment.getUserId()) {
            model.addAttribute("comment", comment);
            return "comments/replyCmt";
        }

        attributes.addAttribute("postId", comment.getPostId());
        return "redirect:/boards/{postId}";
    }

    @PostMapping("/reply/{cmtId}")
    public String addCmtReply(@ModelAttribute Comment comment, RedirectAttributes attributes,
                              @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member) {

        Comment cmt = Comment.builder()
                .cmtContent(comment.getCmtContent())
                .cmtReplyId(comment.getCommentId())
                .userId(member.getUserId())
                .postId(comment.getPostId())
                .build();

        service.cmtReplyWrite(cmt);

        attributes.addAttribute("postId", comment.getPostId());
        return "redirect:/boards/{postId}";
    }

}