package shopping.shop.comment.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import shopping.shop.comment.domain.Comment;
import shopping.shop.member.service.MemberService;
import shopping.shop.post.service.PostService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Commit
@SpringBootTest
class CommentServiceTest {

    @Autowired CommentService commentService;
    @Autowired MemberService memberService;
    @Autowired PostService postService;

    @Test
    @Transactional
    public void commentWrite() {
        Comment comment = Comment.builder()
                .cmtContent("asd")
                .cmtReplyId(0L).build();

        commentService.commentWrite(comment, memberService.findMembers().get(0), postService.getById(3L).getId());
        assertThat(comment.getCmtContent()).isEqualTo("asd");
    }


}