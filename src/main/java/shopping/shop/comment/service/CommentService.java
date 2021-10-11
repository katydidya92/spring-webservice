package shopping.shop.comment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.shop.comment.domain.Comment;
import shopping.shop.comment.repository.CommentRepository;
import shopping.shop.login.repository.LoginRepository;
import shopping.shop.member.domain.Member;
import shopping.shop.post.domain.Post;
import shopping.shop.post.repository.PostRepository;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final LoginRepository loginRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public void commentWrite(Comment comment, Member member, Long postId) {

        Optional<Member> writer = loginRepository.findByLoginId(member.getUserId());
        Optional<Post> board = postRepository.findById(postId);

        comment.setPost(board.get());
        comment.setMember(writer.get());

        commentRepository.save(comment);
    }

    @Transactional
    public void cmtReplyWrite(Comment comment) {
        commentRepository.save(comment);
    }

    public Comment getById(Long cmtId) {
        return commentRepository.getById(cmtId);
    }
}
