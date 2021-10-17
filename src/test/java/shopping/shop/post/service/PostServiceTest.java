package shopping.shop.post.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import shopping.shop.post.domain.Post;
import shopping.shop.post.domain.PostSaveRequestDto;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@Commit
@SpringBootTest
class PostServiceTest {

    @Autowired PostService postService;

    @Test
    @Transactional
    public void updatePost() {
        Post post = postService.getById(3L);

        PostSaveRequestDto postSaveRequestDto =
                PostSaveRequestDto.builder()
                        .title("fsdkjkl")
                        .content("fsdfjk").build();
        postService.updatePost(post.getId(), postSaveRequestDto );

        assertThat(post.getContent()).isEqualTo("fsdfjk");
    }

    @Test
    public void writePost() throws Exception {
        PostSaveRequestDto postSaveRequestDto = PostSaveRequestDto.builder()
                .userId("test!")
                .content("fdsjk")
                .title("fsjekds")
                .build();
        postService.save(postSaveRequestDto);
    }

    @Test
    @Transactional
    public void updateHit() {

        Long postId = postService.getById(4L).getId();
        postService.updateHitById(postId);
        int count = postService.getById(postId).getCount();
        assertThat(count).isEqualTo(1);
    }

}