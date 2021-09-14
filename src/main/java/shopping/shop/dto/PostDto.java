package shopping.shop.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shopping.shop.domain.Post;

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

    public PostDto(Post post) {
        this.id = post.getId();
        this.userId = post.getUserId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.count = post.getCount();
    }

}
