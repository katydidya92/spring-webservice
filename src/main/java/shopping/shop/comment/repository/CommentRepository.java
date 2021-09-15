package shopping.shop.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shopping.shop.comment.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom {
}
