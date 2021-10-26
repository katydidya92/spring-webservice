package shopping.shop.comment.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class CmtResponseDto {

    private Long cmtId;
    private Long cmtReplyId;
    private String cmtContent;
    private String userId;
    private Long postId;

    @Builder
    public CmtResponseDto(Comment entity) {
        this.cmtId = entity.getCmtId();
        this.cmtReplyId = entity.getCmtId();
        this.cmtContent = entity.getCmtContent();
        this.userId = entity.getUserId();
        this.postId = entity.getPostId();
    }
}