package shopping.shop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.shop.domain.Comment;
import shopping.shop.domain.Member;
import shopping.shop.domain.Post;
import shopping.shop.repository.CommentRepository;
import shopping.shop.repository.MemberRepository2;
import shopping.shop.repository.PostRepository;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final MemberRepository2 memberRepository2;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public Long commentWrite(Comment comment, Member member, Long postId) {

        Optional<Member> writer = memberRepository2.findByLoginId(member.getUserId());
        Optional<Post> board = postRepository.findById(postId);

        comment.setPost(board.get());
        comment.setMember(writer.get());

        Comment cmt = commentRepository.save(comment);

        return cmt.getCommentId();
    }

    public Comment getById(Long cmtId) {
        return commentRepository.getById(cmtId);
    }


}
