package shopping.shop.comment.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shopping.shop.common.BaseTimeEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Comment extends BaseTimeEntity {

    @Id @GeneratedValue
    private Long cmtId;
    private String cmtContent;
    private String userId;
    private Long postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cmtReplyId")
    private Comment parent;

    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    private List<Comment> children = new ArrayList<>();

    @Builder
    public Comment(String cmtContent, String userId, Long postId, Comment parent) {
        Comment comment = new Comment();
        this.cmtContent = cmtContent;
        this.userId = userId;
        this.postId = postId;
        this.parent = parent;
    }
}
