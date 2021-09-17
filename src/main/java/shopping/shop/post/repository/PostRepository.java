package shopping.shop.post.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shopping.shop.post.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);

    @Modifying
    @Query("update Post p set p.count = p.count + 1 where p.id = :postId")
    int updateHitById(@Param("postId") Long postId);

    @Modifying
    @Query("update Post p set p.isAvailable = 1 where p.id = :postId")
    int updatePostIsAvailable(@Param("postId") Long postId);
}
