package shopping.shop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import shopping.shop.domain.Comment;
import shopping.shop.domain.Member;
import shopping.shop.domain.MyPageSize;
import shopping.shop.domain.Post;
import shopping.shop.dto.MemberDto;
import shopping.shop.dto.PostDto;
import shopping.shop.service.CommentRepositoryImpl;
import shopping.shop.service.LikeService;
import shopping.shop.service.PostService;
import shopping.shop.session.SessionConst;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final PostService postService;
    private final CommentRepositoryImpl commentService;
    private final LikeService likeService;

    @GetMapping("/add")
    public String openAddPost(Model model) {
        model.addAttribute("article", new Post());
        model.addAttribute("member", new MemberDto());
        return "boards/addPost";
    }

    @PostMapping("/add")
    public String addPost(@Valid @ModelAttribute("article") PostDto postDto, HttpSession session, BindingResult bindingResult, RedirectAttributes attributes) {

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "boards/addPost";
        }

        String userId = (String) session.getAttribute("userId");

        Post post = new Post(userId, postDto.getTitle(), postDto.getContent());

        Post savedPost = postService.save(post);
        attributes.addAttribute("Id", savedPost.getId());
        attributes.addAttribute("status", true);

        return "redirect:/board/{Id}";
    }

    @GetMapping("/list")
    public String openList(Pageable pageable, Model model,
                           @RequestParam(required = false, defaultValue = "") String type,
                           @RequestParam(required = false, defaultValue = "") String keyword) {

        Page<Post> map = postService.search(type, keyword, pageable);

        model.addAttribute("pageSize", MyPageSize.PAGE_SIZE);
        model.addAttribute("posts", map);
        return "boards/list";
    }

    @GetMapping("/{postId}/edit")
    public String openEditPost(@PathVariable Long postId, Model model) {
        Post post = postService.getById(postId);

        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setUserId(post.getUserId());

        model.addAttribute("article", postDto);

        return "boards/editPost";
    }

    @PostMapping("/{postId}/edit")
    public String updatePost(@PathVariable Long postId, @ModelAttribute("article") PostDto postDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "boards/editPost";
        }

        log.info("edit title = {}, detail = {}", postDto.getTitle(), postDto.getContent());

        postService.updatePost(postId, postDto.getTitle(), postDto.getContent());

        return "redirect:/board/{postId}";
    }

    @GetMapping("/{postId}")
    public String openBoard(@PathVariable Long postId, Model model,
                            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member) {

        Post post = postService.getById(postId);

        postService.updateHitById(postId);
        boolean likeCheck = likeService.isNotAlreadyLike(member, post);

        log.info("boardController : likeCheck= {}", likeCheck);

        List<Comment> cmts = commentService.findAllById(postId);
        model.addAttribute("article", post);
        model.addAttribute("cmts", cmts);
        model.addAttribute("member", member);
        model.addAttribute("likeCheck", likeCheck);

        return "boards/post";
    }
    
    /**
     * 테스트용 글 추가
     */
//    @PostConstruct
//    public void init() {
//        for (int i = 1; i < 101; i++) {
//            postService.save(new Post("test!", "a" + i, "b" + i));
//        }
//    }

}
