package shopping.shop.post.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import shopping.shop.post.domain.Post;
import shopping.shop.post.domain.PostDto;
import shopping.shop.post.repository.PostRepository;
import shopping.shop.post.service.PostService;

import java.util.List;
import java.util.Optional;

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