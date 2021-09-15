package shopping.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shopping.shop.domain.Like;
import shopping.shop.domain.Member;
import shopping.shop.domain.Post;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByMemberAndPost(Member member, Post post);
}
