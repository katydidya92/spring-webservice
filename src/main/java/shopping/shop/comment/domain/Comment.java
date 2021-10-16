package shopping.shop.comment.domain;

import lombok.*;
import shopping.shop.domain.BaseTimeEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity {

    @Id @GeneratedValue
    private Long commentId;
    private Long cmtReplyId;
    private String cmtContent;
    private String userId;
    private Long postId;

    @Builder
    public Comment(Long commentId, Long cmtReplyId, String cmtContent, String userId, Long postId) {
        this.commentId = commentId;
        this.cmtReplyId = cmtReplyId;
        this.cmtContent = cmtContent;
        this.userId = userId;
        this.postId = postId;
    }

}
