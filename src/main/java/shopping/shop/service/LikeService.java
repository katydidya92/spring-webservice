package shopping.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shopping.shop.domain.Like;
import shopping.shop.domain.Member;
import shopping.shop.domain.Post;
import shopping.shop.repository.LikeRepository;
import shopping.shop.repository.PostRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;

    public void addLike(Member member, Long postId) {
        Post post = postRepository.getById(postId);

        // 중복 방지
        if (isNotAlreadyLike(member, post)) {
            likeRepository.save(new Like(post, member));
        }
    }

    public void unlike(Member member, Long postId) {
        Post post = postRepository.getById(postId);
        Optional<Like> postLike = likeRepository.findByMemberAndPost(member, post);
        Long likeId = postLike.get().getId();

        if (!isNotAlreadyLike(member, post)) {
            likeRepository.deleteById(likeId);
        }
    }

    public boolean isNotAlreadyLike(Member member, Post post) {
        return likeRepository.findByMemberAndPost(member, post).isEmpty();
    }

}
