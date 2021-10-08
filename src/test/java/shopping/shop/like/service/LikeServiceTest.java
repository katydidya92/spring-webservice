package shopping.shop.like.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import shopping.shop.like.domain.Like;
import shopping.shop.like.repository.LikeRepository;
import shopping.shop.member.domain.Member;
import shopping.shop.member.service.MemberService;
import shopping.shop.post.domain.Post;
import shopping.shop.post.repository.PostRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Commit
@SpringBootTest
class LikeServiceTest {

    @Autowired LikeService likeService;
    @Autowired MemberService memberService;
    @Autowired LikeRepository likeRepository;
    @Autowired PostRepository postRepository;

    @Test
    @Transactional
    public void addLike() {
        Member member = memberService.findOne(1L);
        likeService.addLike(member, 3L);
    }

    @Test
    public void unLike() {
        Post post = postRepository.getById(3L);
        Member member = memberService.findMembers().get(0);
        Optional<Like> postLike = likeRepository.findByMemberAndPost(member, post);
        Long likeId = postLike.get().getId();

        if (!likeService.isNotAlreadyLike(member, post)) {
            likeRepository.deleteById(likeId);
        }
    }


}