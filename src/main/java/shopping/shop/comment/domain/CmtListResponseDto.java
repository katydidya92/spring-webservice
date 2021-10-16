package shopping.shop.comment.domain;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CmtListResponseDto {
    private Long cmtId;
    private Long cmtReplyId;
    private String cmtContent;
    private String userId;
    private Long postId;
    private LocalDateTime modifiedDate;

    public CmtListResponseDto(Comment entity) {
        this.cmtId = entity.getCommentId();
        this.cmtReplyId = entity.getCmtReplyId();
        this.cmtContent = entity.getCmtContent();
        this.userId = entity.getUserId();
        this.postId = entity.getPostId();
        this.modifiedDate = entity.getLastModifiedDate();
    }
}
