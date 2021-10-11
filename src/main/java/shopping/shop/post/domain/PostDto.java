package shopping.shop.post.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shopping.shop.post.domain.Post;

import javax.validation.constraints.NotBlank;

@Getter @Setter
@NoArgsConstructor
public class PostDto {

    private Long id;

    private String userId;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    private int count;

    @Builder
    public PostDto(Post post) {
        this.id = post.getId();
        this.userId = post.getUserId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.count = post.getCount();
    }

}
