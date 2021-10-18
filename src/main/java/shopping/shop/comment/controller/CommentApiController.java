package shopping.shop.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import shopping.shop.comment.domain.CmtListResponseDto;
import shopping.shop.comment.domain.CmtResponseDto;
import shopping.shop.comment.domain.CmtSaveRequestDto;
import shopping.shop.comment.domain.Comment;
import shopping.shop.comment.service.CommentRepositoryImpl;
import shopping.shop.comment.service.CommentService;
import shopping.shop.login.session.SessionConst;
import shopping.shop.member.domain.Member;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentApiController {

    private final CommentService service;
    private final CommentRepositoryImpl cmtService;

    @PostMapping("")
    public Comment addComment(@RequestBody CmtSaveRequestDto dto,
                              @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member) {

        CmtSaveRequestDto cmt = CmtSaveRequestDto.builder()
                .cmtContent(dto.getCmtContent())
                .userId(member.getUserId())
                .postId(dto.getPostId())
                .build();

        return service.commentWrite(cmt);
    }

    @GetMapping("")
    public List<CmtListResponseDto> findAll(Long postId) {
        return cmtService.findAllById(postId);
    }

    @GetMapping("/{id}")
    public CmtResponseDto findById(@PathVariable Long cmtId) {
        return service.findById(cmtId);
    }

    @PutMapping("/{cmtId}")
    public void deleteComment(@PathVariable Long cmtId) {
        cmtService.deleteComment(cmtId);
    }

}