package shopping.shop.comment.repository;

import shopping.shop.comment.domain.CmtListResponseDto;
import shopping.shop.comment.domain.CmtSaveRequestDto;

import java.util.List;

public interface CommentRepositoryCustom  {

    List<CmtListResponseDto> findAllById(Long postId);

    void updateComment(CmtSaveRequestDto comment, Long cmtId);

    void deleteComment(Long cmtId);
}
