package shopping.shop.comment.repository;

import shopping.shop.comment.domain.CmtListResponseDto;
import shopping.shop.comment.domain.Comment;

import java.util.List;

public interface CommentRepositoryCustom  {

    List<CmtListResponseDto> findAllById(Long postId);

    List<CmtListResponseDto> findAllRelistById(Long postId);

    void updateComment(Comment comment, Long cmtId);

    void deleteComment(Long cmtId);
}
