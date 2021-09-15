package shopping.shop.like.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shopping.shop.member.domain.Member;
import shopping.shop.post.domain.Post;

import javax.persistence.*;

@Entity
@Table(name = "likes")
@NoArgsConstructor @Getter @Setter
public class Like {

    @Id @GeneratedValue
    @Column(name = "like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    public Like(Post post, Member member) {
        this.post = post;
        this.member = member;
    }
}
