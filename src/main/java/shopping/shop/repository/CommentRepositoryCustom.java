package shopping.shop.repository;

import shopping.shop.domain.Comment;

import java.util.List;

public interface CommentRepositoryCustom  {

    List<Comment> findAllById(Long postId);

    void updateComment(Comment comment, Long postId);
}
