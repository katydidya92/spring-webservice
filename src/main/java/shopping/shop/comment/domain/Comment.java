package shopping.shop.comment.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import shopping.shop.domain.BaseTimeEntity;
import shopping.shop.member.domain.Member;
import shopping.shop.post.domain.Post;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Comment extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long commentId;

    private String cmtContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private Member member;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

}
