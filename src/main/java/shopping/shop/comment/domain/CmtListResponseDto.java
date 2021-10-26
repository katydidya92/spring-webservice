package shopping.shop.comment.domain;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class CmtListResponseDto {
    private Long cmtId;
    private List<CmtListResponseDto> children = new ArrayList<>();
    private String cmtContent;
    private String userId;
    private Long postId;
    private LocalDateTime modifiedDate;

    public CmtListResponseDto(Comment entity) {
        this.cmtId = entity.getCmtId();
        this.cmtContent = entity.getCmtContent();
        this.userId = entity.getUserId();
        this.postId = entity.getPostId();
        this.modifiedDate = entity.getLastModifiedDate();
    }
}
