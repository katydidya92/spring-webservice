package shopping.shop.like.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shopping.shop.like.domain.Like;
import shopping.shop.member.domain.Member;
import shopping.shop.post.domain.Post;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByMemberAndPost(Member member, Post post);
}
