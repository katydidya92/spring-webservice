package shopping.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shopping.shop.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom {
}
