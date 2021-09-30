package shopping.shop.comment.repository;

import shopping.shop.comment.domain.Comment;

import java.util.List;

public interface CommentRepositoryCustom  {

    List<Comment> findAllById(Long postId);

    List<Comment> findAllRelistById(Long postId);

    void updateComment(Comment comment, Long postId);

    void deleteComment(Long cmtId);
}
