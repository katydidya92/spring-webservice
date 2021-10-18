package shopping.shop.post.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
@NoArgsConstructor
public class PostSaveRequestDto {

    private Long id;

    private String userId;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    private int count;

    @Builder
    public PostSaveRequestDto(Long id, String userId, String title, String content, int count) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.count = count;
    }

    public Post toEntity() {
        return Post.builder()
                .title(title)
                .userId(userId)
                .content(content)
                .build();
    }

}
