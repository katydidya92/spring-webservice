package shopping.shop.comment.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import shopping.shop.comment.domain.CmtSaveRequestDto;
import shopping.shop.member.service.MemberService;
import shopping.shop.post.service.PostService;

import static org.assertj.core.api.Assertions.assertThat;

@Commit
@SpringBootTest
class CommentServiceTest {

    @Autowired CommentService commentService;
    @Autowired MemberService memberService;
    @Autowired PostService postService;

    @Test
    @Transactional
    public void cmtWrite() {
        int num = 10;
        for (int i = 1; i <= num; i++) {
            CmtSaveRequestDto comment = CmtSaveRequestDto.builder()
                    .cmtContent(i+"번 댓글을 추가합니다.")
                    .userId(memberService.findMembers().get(0).getUserId())
                    .postId(102L)
                    .build();
            commentService.commentWrite(comment);
        }
    }

}