package shopping.shop.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import shopping.shop.comment.domain.CmtResponseDto;
import shopping.shop.comment.domain.CmtSaveRequestDto;
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
    public String editComment(@ModelAttribute CmtSaveRequestDto dto, @PathVariable Long cmtId, BindingResult bindingResult,
                              RedirectAttributes attributes, @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member) {

        if (bindingResult.hasErrors() || member.getUserId() == dto.getUserId()) {
            log.info("errors={}", bindingResult);
            return "boards/editPost";
        }

        CmtSaveRequestDto comment = CmtSaveRequestDto.builder()
                .cmtContent(dto.getCmtContent())
                .userId(member.getUserId())
                .postId(dto.getPostId())
                .parentId(dto.getParentId())
                .build();

        cmtService.updateComment(comment, cmtId);
        attributes.addAttribute("postId", dto.getPostId());
        return "redirect:/boards/{postId}";
    }

    @GetMapping("/reply/{cmtId}")
    public String addCmtReplyOpen(Model model, @PathVariable Long cmtId, RedirectAttributes attributes,
                                  @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member) {

        Comment comment = service.getById(cmtId);
        CmtResponseDto.builder()
                .entity(comment)
                .build();

        if (member.getUserId() == comment.getUserId()) {
            model.addAttribute("comment", comment);
            return "comments/replyCmt";
        }

        attributes.addAttribute("postId", comment.getPostId());
        return "redirect:/boards/{postId}";
    }

    @PostMapping("/reply/{cmtId}")
    public String addCmtReply(@ModelAttribute CmtSaveRequestDto dto, RedirectAttributes attributes,
                              @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member) {

        CmtSaveRequestDto cmt = CmtSaveRequestDto.builder()
                .cmtContent(dto.getCmtContent())
                .userId(member.getUserId())
                .postId(dto.getPostId())
                .parentId(dto.getParentId())
                .build();

        log.info("dto:{}", dto.getParentId());
        service.commentWrite(cmt);

        attributes.addAttribute("postId", dto.getPostId());
        return "redirect:/boards/{postId}";
    }

}