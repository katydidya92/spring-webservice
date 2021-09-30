package shopping.shop.comment.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import shopping.shop.domain.BaseTimeEntity;
import shopping.shop.member.domain.Member;
import shopping.shop.post.domain.Post;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Comment extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long commentId;

    private Long cmtReplyId;

    private String cmtContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private Member member;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder
    public Comment(Long cmtReplyId, String cmtContent, Member member, Post post) {
        this.cmtReplyId = cmtReplyId;
        this.cmtContent = cmtContent;
        this.member = member;
        this.post = post;
    }
}
