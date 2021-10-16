package shopping.shop.comment.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class CmtSaveRequestDto {

    private Long cmtReplyId;
    private String cmtContent;
    private String userId;
    private Long postId;

    @Builder
    public CmtSaveRequestDto(Long cmtReplyId, String cmtContent, String userId, Long postId) {
        this.cmtReplyId = cmtReplyId;
        this.cmtContent = cmtContent;
        this.userId = userId;
        this.postId = postId;
    }

    public Comment toEntity() {
        return Comment.builder()
                .cmtReplyId(cmtReplyId)
                .cmtContent(cmtContent)
                .userId(userId)
                .postId(postId)
                .build();
    }
}
