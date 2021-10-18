package shopping.shop.comment.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class CmtSaveRequestDto {

    private Long parentId;
    private String cmtContent;
    private String userId;
    private Long postId;

    @Builder
    public CmtSaveRequestDto(Long parentId, String cmtContent, String userId, Long postId) {
        this.parentId = parentId;
        this.cmtContent = cmtContent;
        this.userId = userId;
        this.postId = postId;
    }

}
