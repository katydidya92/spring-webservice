package shopping.shop.post.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import shopping.shop.post.domain.Post;
import shopping.shop.post.repository.PostRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class BoardApiController {

    private final PostRepository postRepository;

    @GetMapping("/list")
    List<Post> all() {
        return postRepository.findAll();
    }

    @GetMapping("/{postId}")
    Optional<Post> postOne() {
        Optional<Post> post = postRepository.findById(3L);
        return post;
    }

    @DeleteMapping("/{id}")
    void deleteBoard(@PathVariable Long id) {
        postRepository.deleteById(id);
    }

}