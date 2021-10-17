package shopping.shop.post.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import shopping.shop.post.service.PostService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class BoardApiController {

    private final PostService postService;

    @PutMapping("/{postId}")
    public void postDelete(@PathVariable Long postId) {
        postService.updatePostIsAvailable(postId);
    }

}