package shopping.shop.post.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shopping.shop.domain.BaseTimeEntity;
import shopping.shop.comment.domain.Comment;
import shopping.shop.like.domain.Like;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Post extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    private String userId;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @Column(columnDefinition = "integer default 0")
    private int count;

    @Column(columnDefinition = "int default 0")
    private int isAvailable ;

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    Set<Like> likes = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    private List<Comment> comments;

    public Post(String userId, String title, String content) {
        this.userId = userId;
        this.title = title;
        this.content = content;
    }

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
