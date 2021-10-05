package shopping.shop.post.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import shopping.shop.post.domain.Post;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@Commit
@SpringBootTest
class PostServiceTest {

    @Autowired PostService postService;

    @Test
    @Transactional
    public void updatePost() {
        Post post = postService.getById(3L);

        post.setTitle("fshj");
        post.setContent("sdfjksd");

        postService.updatePost(post.getId(), post.getTitle(), post.getContent());

        assertThat(post.getContent()).isEqualTo("sdfjksd");

    }
}